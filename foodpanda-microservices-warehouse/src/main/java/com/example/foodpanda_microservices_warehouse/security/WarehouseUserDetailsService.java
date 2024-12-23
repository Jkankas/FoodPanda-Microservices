package com.example.foodpanda_microservices_warehouse.security;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import com.example.foodpanda_microservices_warehouse.repository.UserProfileReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class WarehouseUserDetailsService implements UserDetailsService {

    @Autowired
    UserProfileReadRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        WarehouseUsers users = repository.findUserByEmail(email);
        if(ObjectUtils.isEmpty(users)){
            throw new UsernameNotFoundException("user not found"+email);
        }

       UserDetailsImplementation implementation =  UserDetailsImplementation.create(users);
//      String pass1 =;
//       String pass2 =  users.getPassword();
//       if(passwordEncoder.matches(pass1,pass2)){
//           return implementation;
//       }
        return implementation;
    }
}
