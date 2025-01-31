package com.example.foodpanda_microservices.service;

import com.example.foodpanda_microservices.dto.entities.AdminEntity;
import com.example.foodpanda_microservices.dto.request.AdminEntityRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AdminService {

     ApiResponse createUser(AdminEntityRequest user);
     Map<String,String> fetchStateCityByPin(int pin);
     Optional<AdminEntity> fetchAdminById(String uniqueId);

}
