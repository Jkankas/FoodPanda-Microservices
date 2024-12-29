package com.example.foodpanda_microservices.service;

import com.example.foodpanda_microservices.dto.request.LoginRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {

    public ApiResponse login(LoginRequest loginRequest);
    public boolean userExists(LoginRequest request);
    public Authentication userAuthentication(LoginRequest request);

}
