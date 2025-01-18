package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.dto.request.UserEntityRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.repository.MenuRepository;
import com.example.foodpanda_microservices.repository.UserProfileJpaRepository;
import com.example.foodpanda_microservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {


    @Autowired
    MenuRepository repository;
    
    @Autowired
    UserProfileJpaRepository userProfileRepository;

    public ApiResponse createUser(UserEntityRequest user){
        repository.createProfile(user.getDepartment(),user.getEmail(),user.getPassword(),
                user.getUsername(),user.getRole());
        return ApiResponse.prepareApiResponse("Successfully Added");
    }
    
    
    
}
