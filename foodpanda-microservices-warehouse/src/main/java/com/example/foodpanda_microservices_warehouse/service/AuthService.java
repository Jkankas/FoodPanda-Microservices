package com.example.foodpanda_microservices_warehouse.service;

import com.example.foodpanda_microservices_warehouse.dto.request.JwtLoginRequest;
import com.example.foodpanda_microservices_warehouse.dto.request.LoginRequestBody;
import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import org.springframework.security.core.Authentication;

public interface AuthService {

    public ApiResponse login(JwtLoginRequest request);
    public boolean userExists(JwtLoginRequest request);
    public Authentication userAuthentication(JwtLoginRequest request);
}
