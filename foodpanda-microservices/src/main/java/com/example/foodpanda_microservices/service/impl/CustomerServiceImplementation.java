package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.configuration.ApplicationProperties;
import com.example.foodpanda_microservices.dto.entities.CustomerEntity;
import com.example.foodpanda_microservices.dto.entities.CustomerLoginMetadata;
import com.example.foodpanda_microservices.dto.entities.CustomerOrder;
import com.example.foodpanda_microservices.dto.request.*;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.CustomerGenerateOtpResponse;
import com.example.foodpanda_microservices.helperClasses.IdGenerator;
import com.example.foodpanda_microservices.helperClasses.PinCodeMaster;
import com.example.foodpanda_microservices.repository.CustomerLoginJpaRepository;
import com.example.foodpanda_microservices.repository.CustomerOrderJpaRepository;
import com.example.foodpanda_microservices.repository.CustomerProfileJpaRepository;
import com.example.foodpanda_microservices.repository.CustomerRepository;
import com.example.foodpanda_microservices.service.CustomerService;
import com.example.foodpanda_microservices.util.JwtUtility;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.ObservationTextPublisher;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.parser.Authorization;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.rmi.MarshalledObject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService {

    @Autowired
    private Logger log;

    @Autowired
    private CustomerLoginJpaRepository customerJpaRepository;

    @Autowired
    private CustomerProfileJpaRepository customerProfileJpaRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOrderJpaRepository customerOrderJpaRepository;

    @Autowired
    private JwtUtility utility;

    @Autowired
   private AdminServiceImplementation adminServiceImplementation;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;


    // Generate OTP
    public CustomerGenerateOtpResponse generateOtp(CustomerGenerateOtpRequest request){
        String phone = request.getPhoneNumber();
        if(!phone.matches("^[6-9]\\d{9}$")){
           throw new IllegalStateException("Invalid phone number. Must start with 6-9 and be 10 digits long.");
        }
        int otp = IdGenerator.generateRandomId();
        log.info("response of OTP,{}",otp);
        CustomerGenerateOtpResponse response = new CustomerGenerateOtpResponse();
        response.setOtp(otp);
        CustomerLoginMetadata loginMetadata = new CustomerLoginMetadata();
        loginMetadata.setPhoneNumber(request.getPhoneNumber());
        loginMetadata.setOtp(response.getOtp());
        loginMetadata.setLogin_at(LocalDateTime.now());
        loginMetadata.setActive(true);
        customerJpaRepository.save(loginMetadata);
        return response;
    }



    // Validate OTP
    public ApiResponse validateOtp(CustomerValidateOtpRequest request) {
        log.info("Inside validate OTP:{}", request);

        String phone = request.getPhoneNumber();
        int otp = request.getOtp();

        Optional<CustomerLoginMetadata> customerLoginMetadata = customerJpaRepository.findByMobile(phone);

        if(customerLoginMetadata.isPresent() && customerLoginMetadata.get().getPhoneNumber().equals(phone)){


            if(!customerLoginMetadata.get().isActive() || customerLoginMetadata.get().getLogin_at().isBefore(LocalDateTime.now().minusMinutes(1))){
                throw new IllegalStateException("Please Log in Again!");
            }

            String metadataPhone = customerLoginMetadata.get().getPhoneNumber();
            int metadataOtp = customerLoginMetadata.get().getOtp();

            if(phone.equals(metadataPhone) && otp == metadataOtp){


                Optional<CustomerEntity> customerEntity1 = customerProfileJpaRepository.findByMobile(phone);
                if(customerEntity1.isEmpty() && !customerIdExists(phone)){
                    String tempId = tempCustomerIdGenerator(phone);
                    CustomerEntity customerEntity = CustomerEntity.builder().phoneNumber(phone).customerId(tempId).build();
                    customerProfileJpaRepository.save(customerEntity);
                }

                String token = utility.generateToken(phone);



              Optional<CustomerLoginMetadata> metadata =  customerJpaRepository.findByMobile(phone);
              CustomerLoginMetadata loginMetadata = metadata.get();
               loginMetadata.setActive(false);


               customerJpaRepository.save(loginMetadata);

                return ApiResponse.prepareApiResponse(token);
            }
            else{
                throw new IllegalStateException("Invalid OTP or Phone Number!");
            }
        }
        else{
            throw new IllegalStateException("Please Login first!");
        }

    }




    // Temporary Customer id Generator
    public String tempCustomerIdGenerator(String phone){
        String prefix = "TEMP";
       return prefix + phone;
    }



    // Check For existing Custom-Id
    public boolean customerIdExists(String phone) {
        try{
           Map<String,Object> customId = customerRepository.searchCustomId(phone);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    public String customerIdGenerator(String stateCode){
        LocalDateTime localDateTime = LocalDateTime.now();
        long epochMili = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String prefix = "CUST";
        return prefix + stateCode + epochMili;
    }



    // Get Customer Profile
    public ApiResponse getCustomerProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String phone = (String) principal;

        Optional<CustomerEntity> customerEntity = customerProfileJpaRepository.findByMobile(phone);
        if(customerEntity.isPresent()){
            return ApiResponse.prepareApiResponse(customerEntity.get());
        }
        throw new IllegalStateException("Customer Not found!");
    }


    // Save Customer Profile
    public ApiResponse saveProfile(CustomerEntityRequest request) {
        log.info("Inside Save Profile Controller :{}", request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String phone = (String) principal;
        log.info("Principal : {}", phone);


        CustomerEntity customerEntity = null;
        Optional<CustomerEntity> entity = customerProfileJpaRepository.findByMobile(phone);
        if (entity.isPresent() && entity.get().getAddress() == null) {
            customerEntity = entity.get();

            Map<String, String> stateCity = adminServiceImplementation.fetchStateCityByPin(request.getPin());
            log.info("State and City : {}", stateCity);
            if (ObjectUtils.isEmpty(stateCity)) {
                throw new IllegalStateException("Invalid Pin-Code!");
            }

            String city = stateCity.get("City");
            String state = stateCity.get("State");

            Map<String, String> stateCodeMap = PinCodeMaster.stateCodeMappingsMaster();
            log.info("State-Code : {}", stateCodeMap);
            String stateCode = stateCodeMap.get(state);
            String customerId = customerIdGenerator(stateCode);

            customerEntity.setFullName(request.getFullName());
            customerEntity.setCustomerId(stateCode);
            customerEntity.setCity(city);
            customerEntity.setState(state);
            customerEntity.setAddress(request.getAddress());
            customerEntity.setLandmark(request.getLandmark());
            customerEntity.setPin(request.getPin());
            customerEntity.setCreated_at(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            customerEntity.setCustomerId(customerId);
            customerEntity.setPhoneNumber(phone);

            customerProfileJpaRepository.save(customerEntity);
        } else {
            throw new IllegalStateException("Customer Already Saved!");
        }

        return ApiResponse.prepareApiResponse(customerProfileJpaRepository.findById(phone));

    }


    // Update Profile
    /*
    User can only update pinCode and Address
     */

    public ApiResponse updateProfile(UpdateProfileRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String phone =  (String) principal;

        Optional<CustomerEntity> customerEntity = customerProfileJpaRepository.findById(phone);
        if(customerEntity.isEmpty()){
            throw new IllegalStateException("Invalid Customer");
        }

        Integer pinCode = request.getPin();
        Map<String,String> pin =  adminServiceImplementation.fetchStateCityByPin(pinCode);
        if(ObjectUtils.isEmpty(pin)){
            return ApiResponse.prepareFailureApiResponse("Invalid PinCode!");
        }
        String state = pin.get("State");
        String city = pin.get("City");

        CustomerEntity entity = customerEntity.get();
        entity.setAddress(request.getAddress());
        entity.setLandmark(request.getLandmark());
        entity.setPin(request.getPin());
        entity.setCity(city);
        entity.setState(state);
        entity.setUpdated_at(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        customerProfileJpaRepository.save(entity);

        return ApiResponse.prepareApiResponse(customerProfileJpaRepository.findById(phone));
    }




    // Deleting Login MetaData
//    public ApiResponse deleteMetadata(String phone){
//        Optional<CustomerLoginMetadata> loginMetadata = customerJpaRepository.findByMobile(phone);
//        if(loginMetadata.isPresent()){
//            customerJpaRepository.delete(loginMetadata.get());
//            return ApiResponse.prepareApiResponse("Successfully Deleted");
//        }
//       return ApiResponse.prepareFailureApiResponse("Customer not found!");
//    }



    // Customer Order
    public ApiResponse customerOrder(CustomerOrderRequest order){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phone = authentication.getPrincipal().toString();

        String dish = order.getDish();
        int quantity = order.getQuantity();

        int stock = checkDishStock(dish);
        log.info("stock,{}",stock);
        if(stock==0){
            throw new IllegalStateException("Dish out of Stock!");
        }

        double price = checkDishPrice(dish);
        log.info("price,{}",dish);
        if(price==0.0){
            throw new IllegalStateException("Dish Price Cannot be 0");
        }

        CustomerEntity customerEntity = customerProfileJpaRepository.findByMobile(phone).get();


        double finalAmount = price * quantity;

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderStatus("Order Placed!");
        customerOrder.setDish(dish);
        customerOrder.setPrice(finalAmount);
        customerOrder.setQuantity(quantity);
        customerOrder.setOrderedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        customerOrder.setCustomerEntity(customerEntity);

        String customer_id = customerOrder.getCustomerEntity().getCustomerId();


        customerOrderJpaRepository.save(customerOrder);
        Optional<CustomerOrder> customerOrder1 = customerOrderJpaRepository.orderByCustomerId(customer_id);
        if(customerOrder1.isPresent()){
            return ApiResponse.prepareApiResponse(customerOrder1);
        }
        return ApiResponse.prepareApiResponse(Optional.empty());
    }





    public int checkDishStock(String dish){
        int finalStocks = 0;
        String url = applicationProperties.getStockByDish();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map<String,String> uriVariables = new HashMap<>();
        uriVariables.put("dish",dish);

        try{
            ResponseEntity<Map<String,Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {
                    },
                    uriVariables
            );

            Map<String,Object> stockResult = response.getBody();
            if(stockResult != null){
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String,Object> stockBody = objectMapper.convertValue(stockResult, new TypeReference<Map<String, Object>>(){});
                Map<String,Object> result = objectMapper.convertValue(stockBody.get("result"), new TypeReference<Map<String, Object>>(){});
                finalStocks = (int) result.get("stock");
            }


        }catch (Exception ex){
            throw new IllegalStateException("Error while fetching stocks!");
        }

       return finalStocks;

    }


    public double checkDishPrice(String dish){
        double finalPrice = 0;

        String url = applicationProperties.getPriceByDish();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map<String,String> uriVariables = new HashMap<>();
        uriVariables.put("dish",dish);

        try{
            ResponseEntity<Map<String,Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {
                    },
                    uriVariables
            );

            Map<String,Object> stockResult = response.getBody();
            if(stockResult != null){
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String,Object> stockBody = objectMapper.convertValue(stockResult, new TypeReference<Map<String, Object>>(){});
                Map<String,Object> result = objectMapper.convertValue(stockBody.get("result"), new TypeReference<Map<String, Object>>(){});
                finalPrice = (double) result.get("price");
            }


        }catch (Exception ex){
            throw new IllegalStateException("Error while fetching price!");
        }

        return finalPrice;
    }

}



