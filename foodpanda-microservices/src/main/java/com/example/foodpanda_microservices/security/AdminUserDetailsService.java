package com.example.foodpanda_microservices.security;
import com.example.foodpanda_microservices.dto.entities.AdminEntity;
import com.example.foodpanda_microservices.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    MenuRepository menuRepository;




    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String,Object> map = menuRepository.userProfile(username);
        AdminEntity entity = new AdminEntity();

        entity.setFullName(((String)map.get("username")));
        entity.setEmail((String)map.get("email"));
        entity.setDepartment((String)map.get("department"));
        entity.setPassword((String)map.get("password"));

        return AdminUserDetailsImplementation.create(entity);
    }



}
