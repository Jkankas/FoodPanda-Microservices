package com.example.foodpanda_microservices_warehouse.security;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import com.example.foodpanda_microservices_warehouse.repository.UserProfileReadRepository;
import com.example.foodpanda_microservices_warehouse.repository.UserProfileRepository;
import com.example.foodpanda_microservices_warehouse.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Component
public class WarehouseUserDetailsService implements UserDetailsService {


    @Autowired
    UserProfileRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        WarehouseUsers users1 = null;
        Optional<WarehouseUsers> users = repository.findByActiveEmail(email);
        WarehouseUsers user = users.orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email)
        );
        return UserDetailsImplementation.create(user);
    }
}
