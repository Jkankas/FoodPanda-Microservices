package com.example.foodpanda_microservices_warehouse.service.impl;
import com.example.foodpanda_microservices_warehouse.dto.request.JwtLoginRequest;
import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import com.example.foodpanda_microservices_warehouse.repository.UserProfileJpaRepository;
import com.example.foodpanda_microservices_warehouse.security.UserDetailsImplementation;
import com.example.foodpanda_microservices_warehouse.service.AuthService;
import com.example.foodpanda_microservices_warehouse.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.Optional;


@Service
public class AuthServiceImplementation implements AuthService {


    @Autowired
    UserProfileJpaRepository userProfileRepository;

    @Autowired
    JwtUtil util;

    @Autowired
    AuthenticationManager authenticationManager;



    @Override
    public ApiResponse login(JwtLoginRequest request) {

        if(ObjectUtils.isEmpty(request.getEmail())|| ObjectUtils.isEmpty(request.getPassword())){
            return ApiResponse.prepareFailApiResponse("Username/Password cannot be null");
        }

        boolean checkUser = userExists(request);
        if (checkUser) {
            try{
                UserDetailsImplementation implementation = (UserDetailsImplementation) userAuthentication(request).getPrincipal();
               String email = implementation.getEmail();
                return ApiResponse.prepareApiResponse(util.generateToken(email));
            }catch (Exception ex){
                return ApiResponse.prepareFailApiResponse(ex.getMessage()+":"+"Incorrect Password");
            }
        }
        return ApiResponse.prepareFailApiResponse("User not found! or Not active");
    }



    public boolean userExists(JwtLoginRequest request){
        Optional<WarehouseUsers> users = userProfileRepository.findByActiveEmail(request.getEmail());
        if(users.isEmpty()){
            return false;
        }
        return true;
    }




    public Authentication userAuthentication(JwtLoginRequest request){
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));
    }

}
