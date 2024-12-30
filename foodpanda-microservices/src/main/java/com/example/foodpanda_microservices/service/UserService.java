package com.example.foodpanda_microservices.service;

import com.example.foodpanda_microservices.dto.entities.UserEntity;
import com.example.foodpanda_microservices.dto.request.UserEntityRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;

public interface UserService {

//    public ApiResponse createUser(UserEntityRequest request);
public ApiResponse createUser(UserEntityRequest user);
}
