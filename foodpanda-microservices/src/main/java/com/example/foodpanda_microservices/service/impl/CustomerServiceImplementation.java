package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.dto.entities.CustomerEntity;
import com.example.foodpanda_microservices.dto.entities.CustomerLoginMetadata;
import com.example.foodpanda_microservices.dto.request.CustomerEntityRequest;
import com.example.foodpanda_microservices.dto.request.CustomerGenerateOtpRequest;
import com.example.foodpanda_microservices.dto.request.CustomerValidateOtpRequest;
import com.example.foodpanda_microservices.dto.request.UpdateProfileRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.CustomerGenerateOtpResponse;
import com.example.foodpanda_microservices.helperClasses.IdGenerator;
import com.example.foodpanda_microservices.helperClasses.PinCodeMaster;
import com.example.foodpanda_microservices.repository.CustomerLoginJpaRepository;
import com.example.foodpanda_microservices.repository.CustomerProfileJpaRepository;
import com.example.foodpanda_microservices.repository.CustomerRepository;
import com.example.foodpanda_microservices.service.CustomerService;
import com.example.foodpanda_microservices.util.JwtUtility;
import io.micrometer.observation.ObservationTextPublisher;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
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
    private JwtUtility utility;

    @Autowired
    AdminServiceImplementation adminServiceImplementation;


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

        if(customerLoginMetadata.isPresent()){

//            if(customerLoginMetadata.get().getLogin_at().isBefore(LocalDateTime.now().minusMinutes(1))
//            && customerLoginMetadata.get().isActive()){
//                throw new IllegalStateException("OTP Expired!");
//            }


            if(!customerLoginMetadata.get().isActive()){
                throw new IllegalStateException("Please Log in again!");
            }

            String metadataPhone = customerLoginMetadata.get().getPhoneNumber();
            int metadataOtp = customerLoginMetadata.get().getOtp();

            if(phone.equals(metadataPhone) && otp == metadataOtp){
                String token = utility.generateToken(phone);
                String tempId = tempCustomerIdGenerator(phone);
                CustomerEntity customerEntity = CustomerEntity.builder().phoneNumber(phone).customerId(tempId).build();
                customerProfileJpaRepository.save(customerEntity);

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

}
