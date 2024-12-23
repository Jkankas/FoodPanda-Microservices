package com.example.foodpanda_microservices_warehouse.security;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import com.example.foodpanda_microservices_warehouse.repository.UserProfileReadRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class UserDetailsImplementation implements UserDetails {


    private Long id;
    private String email;
//    private String username;
    private String password;
    private Collection<? extends  GrantedAuthority> authorities;


    private UserDetailsImplementation(Long id , String email,String password, Collection<? extends GrantedAuthority> authorities){
        this.id = id;
        this.email = email;
//        this.username = email;
        this.password = password;
        this.authorities = authorities;
    }




    public static UserDetailsImplementation create(WarehouseUsers users){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(users.getDepartment()));


        if(users.getDepartment()!=null){
            return new UserDetailsImplementation(users.getId(),users.getEmail()
                    ,users.getPassword(),authorities);
        }
        return new UserDetailsImplementation(users.getId(),users.getEmail(),
                users.getUsername(),authorities);
    }



//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
    @Override
    public String getUsername() {
        return email;
    }
}
