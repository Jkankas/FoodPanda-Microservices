package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.dto.entities.CustomerLoginMetadata;
import com.example.foodpanda_microservices.dto.request.CustomerGenerateOtpRequest;
import com.example.foodpanda_microservices.dto.request.CustomerValidateOtpRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.CustomerGenerateOtpResponse;
import com.example.foodpanda_microservices.helperClasses.IdGenerator;
import com.example.foodpanda_microservices.repository.CustomerJpaRepository;
import com.example.foodpanda_microservices.service.CustomerService;
import com.example.foodpanda_microservices.util.JwtUtility;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService {

    @Autowired
    private Logger log;

    @Autowired
    CustomerJpaRepository customerJpaRepository;

    @Autowired
    JwtUtility utility;


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
        customerJpaRepository.save(loginMetadata);
        return response;
    }



    // Validate OTP
    public ApiResponse validateOtp(CustomerValidateOtpRequest request){
        CustomerLoginMetadata loginMetadata = new CustomerLoginMetadata();
        String phone = request.getPhoneNumber();
        String OTP = request.getOtp();
        Optional<CustomerLoginMetadata> metadata = customerJpaRepository.findById(phone);
        if(metadata.isPresent()){
           loginMetadata = metadata.get();
           String loginPhone = loginMetadata.getPhoneNumber();
           int loginOtp = loginMetadata.getOtp();
           if(loginPhone.matches(phone) && OTP.matches(String.valueOf(loginOtp))) {
               String token = utility.generateToken(loginPhone);
               return ApiResponse.prepareApiResponse(token);
           }
           else {
               return ApiResponse.prepareFailureApiResponse("Invalid OTP/MobileNumber!");
           }
        }
        else{
           Optional<CustomerLoginMetadata> emptyData = Optional.empty();
          throw new IllegalStateException("Phone Number not registered!");
        }

    }




}
