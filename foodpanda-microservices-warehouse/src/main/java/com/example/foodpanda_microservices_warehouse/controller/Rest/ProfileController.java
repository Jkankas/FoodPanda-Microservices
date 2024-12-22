package com.example.foodpanda_microservices_warehouse.controller.Rest;

import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import com.example.foodpanda_microservices_warehouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouse")
public class ProfileController {

    @Autowired
    UserService service;


    @PostMapping("/addProfile")
    public ResponseEntity<ApiResponse> saveProfile( @RequestBody WarehouseUsers users){
        WarehouseUsers users1 = service.addProfile(users);
        return new ResponseEntity<>(ApiResponse.prepareApiResponse("Profile added"),
                HttpStatus.OK);
    }


}
