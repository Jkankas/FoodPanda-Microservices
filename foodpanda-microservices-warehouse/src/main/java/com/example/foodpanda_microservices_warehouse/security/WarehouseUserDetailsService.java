package com.example.foodpanda_microservices_warehouse.security;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import com.example.foodpanda_microservices_warehouse.repository.UserProfileJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WarehouseUserDetailsService implements UserDetailsService {


    @Autowired
    UserProfileJpaRepository repository;


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
