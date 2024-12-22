package com.example.foodpanda_microservices_warehouse.security;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import com.example.foodpanda_microservices_warehouse.repository.UserProfileReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

public class WarehouseUserDetailsService implements UserDetailsService {

    @Autowired
    UserProfileReadRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        WarehouseUsers users = repository.findUserByEmail(email);
        if(ObjectUtils.isEmpty(users)){
            throw new UsernameNotFoundException("user not found"+email);
        }
        return UserDetailsImplementation.create(users);
    }
}
