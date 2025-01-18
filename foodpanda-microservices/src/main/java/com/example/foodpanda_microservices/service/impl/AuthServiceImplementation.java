package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.dto.request.LoginRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.repository.MenuRepository;
import com.example.foodpanda_microservices.security.AdminUserDetailsImplementation;
import com.example.foodpanda_microservices.service.AuthService;
import com.example.foodpanda_microservices.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Service
public class AuthServiceImplementation implements AuthService {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    MenuRepository repository;

    public ApiResponse login(LoginRequest loginRequest){
        if(ObjectUtils.isEmpty(loginRequest.getUsername())|| ObjectUtils.isEmpty(loginRequest.getPassword())){
            return ApiResponse.prepareFailureApiResponse("Username/Password cannot be Null/Empty");
        }
        if(userExists(loginRequest)){
            try{
                AdminUserDetailsImplementation implementation = (AdminUserDetailsImplementation)userAuthentication(loginRequest).getPrincipal();
                return ApiResponse.prepareApiResponse(jwtUtility.generateToken(implementation.getEmail()));
            }catch (Exception e){
                return ApiResponse.prepareFailureApiResponse(e.getMessage() + " "+ "Incorrect Password");
            }
        }
     return ApiResponse.prepareFailureApiResponse("User not Found!");
    }



    public boolean userExists(LoginRequest request){
        try{
            Map<String,Object> users=repository.userProfile(request.getUsername());
            if(!ObjectUtils.isEmpty(users)){
                return true;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }



    public Authentication userAuthentication(LoginRequest request){
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword()));
    }


}
