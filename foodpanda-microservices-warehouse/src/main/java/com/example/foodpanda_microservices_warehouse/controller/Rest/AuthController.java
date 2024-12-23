package com.example.foodpanda_microservices_warehouse.controller.Rest;

import com.example.foodpanda_microservices_warehouse.dto.request.JwtLoginRequest;
import com.example.foodpanda_microservices_warehouse.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/warehouse/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;


    @Autowired
    PasswordEncoder passwordEncoder;



    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody JwtLoginRequest request) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Retrieve authenticated user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();


            // Generate token
            String token = jwtUtil.generateToken(userDetails.getUsername());
            System.out.println(token);

            // Build response
            Map<String, Object> response = Map.of(
                    "token", token,
                    "department", userDetails.getAuthorities()
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }

}
