package com.example.foodpanda_microservices_warehouse.security;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
public class UserDetailsImplementation implements UserDetails {

    private Long id;
    private String email;
    private String username;
    private Collection<? extends  GrantedAuthority> authorities;


    private UserDetailsImplementation(Long id , String email, String username , Collection<? extends GrantedAuthority> authorities){
        this.id = id;
        this.email = email;
        this.username = username;
        this.authorities = authorities;
    }




    public static UserDetailsImplementation create(WarehouseUsers users){
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(users.getDepartment()));

        if(users.getDepartment()!=null){
            return new UserDetailsImplementation(users.getId(),users.getEmail(),
                    users.getUsername(),authorityList);
        }
        return new UserDetailsImplementation(users.getId(),users.getEmail(),
                users.getUsername(),authorityList);
    }







    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
}
