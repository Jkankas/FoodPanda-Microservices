package com.example.foodpanda_microservices_warehouse.service.impl;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import com.example.foodpanda_microservices_warehouse.repository.UserProfileJpaRepository;
import com.example.foodpanda_microservices_warehouse.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImplementation implements UserService {


    @Autowired
    private UserProfileJpaRepository repository;

        public WarehouseUsers addProfile(WarehouseUsers users){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String pass = passwordEncoder.encode(users.getPassword());
            users.setPassword(pass);
            return repository.save(users);
        }
}
