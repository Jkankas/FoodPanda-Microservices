package com.example.foodpanda_microservices.service;

import com.example.foodpanda_microservices.dto.request.CustomerGenerateOtpRequest;
import com.example.foodpanda_microservices.dto.request.CustomerValidateOtpRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.CustomerGenerateOtpResponse;

public interface CustomerService {

    CustomerGenerateOtpResponse generateOtp(CustomerGenerateOtpRequest request);
    ApiResponse validateOtp(CustomerValidateOtpRequest request);

}