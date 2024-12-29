package com.example.foodpanda_microservices.security;
import com.example.foodpanda_microservices.dto.pojo.UserEntity;
import com.example.foodpanda_microservices.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    MenuRepository menuRepository;




    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String,Object> map = menuRepository.userProfile(username);
        UserEntity entity = new UserEntity();

        entity.setUserName((String)map.get("username"));
        entity.setEmail((String)map.get("email"));
        entity.setDepartment((String)map.get("department"));
        entity.setPassword((String)map.get("password"));

        return AdminUserDetailsImplementation.create(entity);
    }



}
