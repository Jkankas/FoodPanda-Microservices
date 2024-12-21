package com.example.foodpanda_microservices.service;
import com.example.foodpanda_microservices.repository.MenuRepository;
import io.jsonwebtoken.security.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    MenuRepository menuRepository;




    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Map<String,Object>> list = menuRepository.userProfile();
        for(Map<String,Object> map : list){
            if(map.get("username").equals(username)){
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority((String)map.get("role")));
                System.out.println(authorities);
                return new User(username,(String)map.get("password"), authorities);

            }
        }
        return null;
    }

}
