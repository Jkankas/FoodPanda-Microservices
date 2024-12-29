package com.example.foodpanda_microservices.controller.Rest;

import com.example.foodpanda_microservices.dto.request.LoginRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.security.AdminUserDetailsImplementation;
import com.example.foodpanda_microservices.service.AuthService;
import com.example.foodpanda_microservices.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {


    @Autowired
    AuthService service;

    @PostMapping("/login")
    public ApiResponse login( @RequestBody LoginRequest request){
        return service.login(request);
    }


}


//            return ResponseEntity.ok(Map.of("token",jwtUtility.generateToken(userDetailsImplementation.getEmail()),
//                    "authorities",userDetailsImplementation.getAuthorities().toString()
//                    ));