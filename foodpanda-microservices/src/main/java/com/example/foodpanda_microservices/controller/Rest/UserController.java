package com.example.foodpanda_microservices.controller.Rest;

import com.example.foodpanda_microservices.dto.entities.UserEntity;
import com.example.foodpanda_microservices.dto.request.UserEntityRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    UserService userService;


//    @PostMapping("/createUser")
//    public ResponseEntity<ApiResponse> createUser(@RequestBody UserEntityRequest request){
//        return new ResponseEntity<>(userService.createUser(request), HttpStatus.OK);
//    }


    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserEntityRequest user){
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }


}
