package com.example.foodpanda_microservices.security;

import com.example.foodpanda_microservices.dto.entities.UserEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
public class AdminUserDetailsImplementation implements UserDetails {


    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    private AdminUserDetailsImplementation(String email , String password , Collection<? extends GrantedAuthority> authorities){
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }



    public static AdminUserDetailsImplementation create(UserEntity entity){
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(entity.getDepartment()));

        if(!ObjectUtils.isEmpty(entity.getDepartment())){
            return new AdminUserDetailsImplementation(entity.getEmail(), entity.getPassword()
            ,authorityList);
        }
        return new AdminUserDetailsImplementation(entity.getEmail(), entity.getPassword(), authorityList);
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
