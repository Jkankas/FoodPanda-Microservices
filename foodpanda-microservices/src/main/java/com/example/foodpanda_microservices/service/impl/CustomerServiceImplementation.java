package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.configuration.ApplicationProperties;
import com.example.foodpanda_microservices.dto.entities.CustomerEntity;
import com.example.foodpanda_microservices.dto.entities.CustomerLoginMetadata;
import com.example.foodpanda_microservices.dto.entities.CustomerOrder;
import com.example.foodpanda_microservices.dto.entities.OrderInvoiceInfo;
import com.example.foodpanda_microservices.dto.request.*;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.CustomerGenerateOtpResponse;
import com.example.foodpanda_microservices.dto.response.UpdateStockResponse;
import com.example.foodpanda_microservices.enums.OrderStatus;
import com.example.foodpanda_microservices.enums.PaymentStatus;
import com.example.foodpanda_microservices.helperClasses.IdGenerator;
import com.example.foodpanda_microservices.helperClasses.PinCodeMaster;
import com.example.foodpanda_microservices.repository.CustomerLoginJpaRepository;
import com.example.foodpanda_microservices.repository.CustomerOrderJpaRepository;
import com.example.foodpanda_microservices.repository.CustomerProfileJpaRepository;
import com.example.foodpanda_microservices.repository.CustomerRepository;
import com.example.foodpanda_microservices.service.CustomerService;
import com.example.foodpanda_microservices.service.InvoiceService;
import com.example.foodpanda_microservices.service.MenuService;
import com.example.foodpanda_microservices.util.JwtUtility;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.awt.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.rmi.MarshalledObject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

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

    @Autowired
    private MenuService menuService;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaConsumer kafkaConsumer;

//    @Autowired
//    private InvoiceService invoiceService;


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

        String dish = URLDecoder.decode(order.getDish(), StandardCharsets.UTF_8);
        int quantity = order.getQuantity();
        String orderStatus = (order.getOrderStatus().equals(OrderStatus.CONFIRMED.toString()) ?
                OrderStatus.ORDER_PLACED.toString() : OrderStatus.CANCELLED.toString());

        int stock = checkDishStock(dish);
        log.info("stock,{}",stock);
        if(stock==0){
            throw new IllegalStateException("Dish out of Stock!");
        }

        updateStock(quantity,dish);

        double price = checkDishPrice(dish);
        log.info("price,{}",dish);
        if(price==0.0){
            throw new IllegalStateException("Dish Price Cannot be 0");
        }

        CustomerEntity customerEntity = customerProfileJpaRepository.findByMobile(phone).get();

        double finalAmount = price * quantity;

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderStatus(orderStatus);
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


    //Refactor the code tomorrow(03/04/2025)

    @Transactional
    public ApiResponse customerOrderV1(CustomerOrderRequestNew order){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phone = authentication.getPrincipal().toString();

        if(order.getDishes().size()!=order.getQuantities().size()){
            return ApiResponse.prepareFailureApiResponse("Quantities & Order Mismatch");
        }


        Map<String,Integer> items = new HashMap<>();
        List<String> dishes = order.getDishes();
        List<Integer> quantities = order.getQuantities();

        for(int i=0;i<quantities.size();i++){
            items.put(dishes.get(i),quantities.get(i));
        }
        log.info("Items list ,{}",items);

        String orderStatus = (order.getOrderStatus().equals(OrderStatus.CONFIRMED.toString()) ?
                OrderStatus.ORDER_PLACED.toString() : OrderStatus.CANCELLED.toString());

        List<Integer> stockList = getStocksInfo(order, items);
        updateStockV1(order.getDishes(),stockList);

        Map<String,Double> finalAmount = new HashMap<>();
        List<Map<String,Object>> dishPrices =  checkDishPriceV1(order.getDishes());
        for(Map<String,Object> map : dishPrices){
           double price = (double) map.get("price")* items.get(map.get("dish"));
           finalAmount.put( (String)map.get("dish"),price);
       }
        CustomerEntity customerEntity = customerProfileJpaRepository.findByMobile(phone)
                .orElseThrow(()-> new IllegalArgumentException("Customer Not found with this Number"+phone));

        List<CustomerOrder> customerOrderList = new ArrayList<>();
        pojoToEntityConverter(dishes, orderStatus, finalAmount, quantities, customerEntity, customerOrderList);
        if(!ObjectUtils.isEmpty(customerOrderList)){
            return ApiResponse.prepareApiResponse(customerOrderList);
        }
        return ApiResponse.prepareApiResponse(Optional.empty());
    }



    private void pojoToEntityConverter(List<String> dishes, String orderStatus, Map<String, Double> finalAmount, List<Integer> quantities, CustomerEntity customerEntity, List<CustomerOrder> customerOrderList) {
        for(int i = 0; i< dishes.size(); i++){
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setOrderStatus(orderStatus);
            customerOrder.setDish(dishes.get(i));
            customerOrder.setPrice(finalAmount.get(dishes.get(i)));
            customerOrder.setQuantity(quantities.get(i));
            customerOrder.setOrderedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            customerOrder.setCustomerEntity(customerEntity);
            customerOrder =  customerOrderJpaRepository.save(customerOrder);
            customerOrderList.add(customerOrder);
        }
    }



    private List<Integer> getStocksInfo(CustomerOrderRequestNew order, Map<String, Integer> items) {
        Map<String,Object> stockResult =  checkDishStockV1(order.getDishes());
        log.info("order List,{}", order.getDishes());

        List<Integer> stockList = new ArrayList<>();

        for(Map.Entry<String,Object> map : stockResult.entrySet()){
            if((Integer)map.getValue()==0){
                throw new IllegalStateException("Dish out of Stock: "+map.getKey());
            }
            else{
                String key = map.getKey();
                int val = (int) map.getValue();
                int finalStock = val -  items.get(key);
                stockList.add(finalStock);
            }
        }
        return stockList;
    }


    @Override
    public boolean updateOrder(Long id, String status) {
        Optional<Integer> order_status = customerOrderJpaRepository.updateOrderStatus(id,status);
        Optional<CustomerOrder> orderDetails = customerOrderJpaRepository.getOrderDetails(id);
        CustomerOrder customerOrder = orderDetails.get();
        String invoiceNumber = customerOrder.getOrderId() + "FP"+ customerOrder.getCustomerEntity().getCustomerId()+".pdf";
        kafkaProducer.sendMessage(invoiceNumber);

        System.out.println(order_status);
        if(order_status.isPresent() && order_status.get()>0){
            menuService.uploadInvoiceFromBase64(id);
//            menuService.uploadInvoiceUpdated(id,invoiceNumber);
//            menuService.generateInvoice(invoiceNumber,id);
            return true;
        }
        return false;
    }




    public ApiResponse updateOrderV1(UpdateOrderV1Request request) {
        Optional<List<CustomerOrder>> getBulkOrderDetails;
        Optional<CustomerEntity> CustomerDetails;
        List<CustomerOrder> orderDetailsList = List.of();
        String invoiceNo="";

        List<Map<String, Object>> orderDetailsList1;
        Map<String, Object> customerDetailsMap;
        if (OrderStatus.DELIVERED.toString().equals(request.getStatus())) {
            try {
                Optional<Integer> updateBulkOrder = customerOrderJpaRepository.updateBulkOrderStatus(request.getOrderIds(), request.getStatus());
                if (updateBulkOrder.isEmpty()) {
                    throw new IllegalStateException("Error while Updating Orders Status");
                }
                getBulkOrderDetails = customerOrderJpaRepository.getBulkOrderDetails(request.getOrderIds());
                if (getBulkOrderDetails.isPresent()) {
                    orderDetailsList = getBulkOrderDetails.get();
                } else {
                    throw new IllegalStateException("Error fetching Order Details!");
                }

                orderDetailsList1 = new ArrayList<>();
                String customerId = null;

                String orderId = null;
                for (CustomerOrder customerOrder : orderDetailsList) {
                    customerId = customerOrder.getCustomerEntity().getCustomerId();
                    Map<String, Object> orderDetails = new HashMap<>();
                    orderDetails.put("orderId", customerOrder.getOrderId());
                    orderDetails.put("dish", customerOrder.getDish());
                    orderDetails.put("price", customerOrder.getPrice());
                    orderDetails.put("quantity", customerOrder.getQuantity());
                    orderDetailsList1.add(orderDetails);
                }

                CustomerEntity customerEntity = null;
                CustomerDetails = customerProfileJpaRepository.findByCustomerId(customerId);
                if (CustomerDetails.isPresent()) {
                    customerEntity = CustomerDetails.get();
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                customerDetailsMap = new HashMap<>();
                assert customerEntity != null;
                 invoiceNo = customerEntity.getCustomerId() + "Id" + IdGenerator.generateRandomId();
                customerDetailsMap.put("address", customerEntity.getAddress());
                customerDetailsMap.put("state", customerEntity.getState());
                customerDetailsMap.put("city", customerEntity.getCity());
                customerDetailsMap.put("pin", customerEntity.getPin());
                customerDetailsMap.put("phone", customerEntity.getPhoneNumber());
                customerDetailsMap.put("fullName", customerEntity.getFullName());
                customerDetailsMap.put("invoiceNo", invoiceNo);
                customerDetailsMap.put("date", LocalDateTime.now().format(formatter));



            } catch (Exception e) {
                throw new IllegalStateException("Error Updating Orders");
            }
        } else {
            return ApiResponse.prepareFailureApiResponse("Invalid Order Status");
        }
        menuService.uploadInvoiceFromBase64V1(orderDetailsList1, customerDetailsMap);
        return ApiResponse.prepareApiResponse(invoiceNo);
    }

    @Override
    public ApiResponse checkPaymentStatus(long orderId) {
        CustomerOrder orderDetails = null;
        Optional<CustomerOrder> customerOrder = customerOrderJpaRepository.getOrderDetails(orderId);
        if(customerOrder.isPresent()){
            orderDetails = customerOrder.get();
        }
        else{
            return ApiResponse.prepareFailureApiResponse("No Order Details Found");
        }

        if(ObjectUtils.isEmpty(orderDetails.getPaymentStatus())){
            return ApiResponse.prepareFailureApiResponse("Payment not done yet");
        }

        if(!orderDetails.getOrderStatus().isEmpty() &&
                (
                orderDetails.getPaymentStatus().equals(PaymentStatus.PAID)) ||
                orderDetails.getPaymentStatus().equals(PaymentStatus.HOLD) ||
                orderDetails.getPaymentStatus().equals(PaymentStatus.CANCELLED) ||
                orderDetails.getPaymentStatus().equals(PaymentStatus.ERROR)
        )
        {
//            String status = PaymentStatus.HOLD.name();
//            PaymentStatus status1 = PaymentStatus.valueOf("HOLD");
            return ApiResponse.prepareApiResponse(orderDetails.getPaymentStatus());

        }
        return null;
    }




    /*
        Update Order V1 requirements:
        Items Info:
        Order No.
        dish Name
        Quantity
        Amount
        Invoice Number

          Address Info:
        Customer Add.(To)
        City
        state
        Company Address(From)

        Total Payable amount

     */


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


    public Map<String,Object> checkDishStockV1(List<String> request){
      final  Map<String,Object> stockList = new HashMap<>();
        int finalStocks = 0;
        String url = applicationProperties.getStockByDishV1();
        HttpHeaders headers = new HttpHeaders();
        Map<String,Object> req = new HashMap<>();
        req.put("dishes",request);
        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(req,headers);

        try{
            ResponseEntity<Map<String,Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {
                    }
            );

            Map<String,Object> stockResult = response.getBody();
            if(stockResult != null){
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String,Object> stockBody = objectMapper.convertValue(stockResult, new TypeReference<>() {
                });
                List<Map<String,Object>> result = objectMapper.convertValue(stockBody.get("result"), new TypeReference<>() {
                });
                if(result != null){
//                     stockList = objectMapper.convertValue(result, new TypeReference<>() {
//                    });
                    for(Map<String,Object> map : result){
                       String dish = (String)map.get("dish");
                       Integer stock = (Integer)map.get("stock");

                       if(dish!=null){
                           stockList.put(dish,stock);
                       }
                    }
                }
            }
        }catch (Exception ex){
            throw new IllegalStateException("Error while fetching stocks!");
        }
        return stockList;
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
                    new ParameterizedTypeReference<>() {},
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



    public List<Map<String,Object>> checkDishPriceV1(List<String> dishes){

        List<Map<String,Object>> resultSet = new ArrayList<>();

        String url = applicationProperties.getPriceByDishV1();
        HttpHeaders headers = new HttpHeaders();
        Map<String,Object> dishRequest = new HashMap<>();
        dishRequest.put("dishes",dishes);
        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(dishRequest,headers);

        try{
            ResponseEntity<Map<String,Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );

            Map<String,Object> stockResult = response.getBody();
            if(stockResult != null){
                ObjectMapper objectMapper = new ObjectMapper();
                 resultSet = objectMapper.convertValue(stockResult.get("result"), new TypeReference<List<Map<String,Object>>>() {
                });
                 log.info("resultSet,{}",resultSet);
            }
        }catch (Exception ex){
            throw new IllegalStateException("Error while fetching price!");
        }
        return resultSet ;
    }



    public void updateStock(int stock,String dish){
        String url = applicationProperties.getUpdateStock();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        String finalUrl = UriComponentsBuilder.fromHttpUrl(url).queryParam("stock",stock)
                .queryParam("dish",dish).toUriString();


        try{
            ResponseEntity<Map<String,Object>> response = restTemplate.exchange(
                    finalUrl,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>(){}
            );

            Map<String,Object> resultSet = response.getBody();
            if(resultSet!=null){
                ObjectMapper mapper = new ObjectMapper();
                Map<String,Object> resultBody = mapper.convertValue(resultSet, new TypeReference<Map<String, Object>>() {});
                if(!resultBody.get("message").equals("success") && !((int)resultBody.get("result")==1)){
                    throw new IllegalStateException("Couldn't Update stocks!");
                }
            }

        }catch (Exception e){
            throw new IllegalStateException("Error while Updating stocks");
        }

    }


    public void updateStockV1(List<String> dishes , List<Integer> stocks){
        String url = applicationProperties.getUpdateStockV1();
        HttpHeaders headers = new HttpHeaders();


        UpdateStockResponse response = new UpdateStockResponse();
        response.setDishes(dishes);
        response.setStocks(stocks);


        HttpEntity<UpdateStockResponse> entity = new HttpEntity<>(response,headers);

        try{
            ResponseEntity<Map<String,Object>> response1 = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>(){}
            );

            Map<String,Object> resultSet = response1.getBody();
            if(resultSet!=null){
                ObjectMapper mapper = new ObjectMapper();
                Map<String,Object> resultBody = mapper.convertValue(resultSet, new TypeReference<Map<String, Object>>() {});
                if(!resultBody.get("message").equals("success") && !((int)resultBody.get("result")>=1)){
                    throw new IllegalStateException("Couldn't Update stocks!");
                }
            }

        }catch (Exception e){
            throw new IllegalStateException("Error while Updating stocks");
        }

    }

    // Update Order Status based on orderId




}



