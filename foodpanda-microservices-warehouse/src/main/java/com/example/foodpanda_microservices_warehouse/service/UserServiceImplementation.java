package com.example.foodpanda_microservices_warehouse.service;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import com.example.foodpanda_microservices_warehouse.repository.UserProfileRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService{


    @Autowired
    private UserProfileRepository repository;

        public WarehouseUsers addProfile(WarehouseUsers users){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String pass = passwordEncoder.encode(users.getPassword());
            users.setPassword(pass);
            return repository.save(users);
        }

}
