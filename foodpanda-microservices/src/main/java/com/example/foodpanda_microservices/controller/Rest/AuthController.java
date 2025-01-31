package com.example.foodpanda_microservices.controller.Rest;

import com.example.foodpanda_microservices.dto.request.CustomerGenerateOtpRequest;
import com.example.foodpanda_microservices.dto.request.CustomerValidateOtpRequest;
import com.example.foodpanda_microservices.dto.request.LoginRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.CustomerGenerateOtpResponse;
import com.example.foodpanda_microservices.security.AdminUserDetailsImplementation;
import com.example.foodpanda_microservices.service.AuthService;
import com.example.foodpanda_microservices.service.CustomerService;
import com.example.foodpanda_microservices.util.JwtUtility;
import jakarta.validation.Valid;
import org.slf4j.Logger;
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
    private Logger log;

    @Autowired
    CustomerService customerService;

    @Autowired
    AuthService adminService;

    @PostMapping("admin/login")
    public ApiResponse login( @RequestBody LoginRequest request){
        return adminService.login(request);
    }



    //Customer Log in-Generate OTP
    @PostMapping("customer/logIn")
    public ApiResponse generateOtp(@Valid @RequestBody CustomerGenerateOtpRequest request){
        log.info("request received at Generate-OTP-controller,{}",request);
        CustomerGenerateOtpResponse response = customerService.generateOtp(request);
        return ApiResponse.prepareApiResponse(response.getOtp());
    }



    //Customer Log in-Validate OTP
    @PostMapping("customer/validateOtp")
    public ApiResponse validateOtp( @Valid @RequestBody CustomerValidateOtpRequest request){
        return customerService.validateOtp(request) ;
    }

}