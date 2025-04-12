package com.example.foodpanda_microservices.service;

import com.example.foodpanda_microservices.dto.entities.CustomerOrder;
import com.example.foodpanda_microservices.dto.request.*;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.CustomerGenerateOtpResponse;

public interface CustomerService {

    CustomerGenerateOtpResponse generateOtp(CustomerGenerateOtpRequest request);
    ApiResponse validateOtp(CustomerValidateOtpRequest request);
    ApiResponse getCustomerProfile();
    ApiResponse saveProfile(CustomerEntityRequest request);
    ApiResponse updateProfile(UpdateProfileRequest request);
    ApiResponse customerOrder(CustomerOrderRequest order);
    ApiResponse customerOrderV1(CustomerOrderRequestNew order);
    boolean updateOrder(Long id,String status);
//     ApiResponse deleteMetadata(String phone);

}