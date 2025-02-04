package com.example.foodpanda_microservices.controller.Rest;

import com.example.foodpanda_microservices.dto.entities.AdminEntity;
import com.example.foodpanda_microservices.dto.request.AdminEntityRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.repository.AdminJpaRepository;
import com.example.foodpanda_microservices.service.AdminService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private Logger log;

    @Autowired
    private AdminService adminService;



    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse> createUser(  @Valid @RequestBody AdminEntityRequest user){
        log.info("request received at Add Menu Controller,{}",user);
        return new ResponseEntity<>(adminService.createUser(user), HttpStatus.OK);
    }




    @GetMapping("fetchStateCityByPin/{pin}")
    public ApiResponse fetchStateCityByPinCode( @PathVariable int pin){
        log.info("request received at PinCode-Admin-Service-Impl,{}",pin);
        Object result = adminService.fetchStateCityByPin(pin);
        if(ObjectUtils.isEmpty(result)){
            return ApiResponse.prepareFailureApiResponse("Invalid PinCode!");
        }
        return ApiResponse.prepareApiResponse(result);
    }



    @GetMapping("/fetchAdminById/{id}")
    public ApiResponse fetchAdminDataByUniqueId(@PathVariable String id){
        Optional<AdminEntity> adminEntity = adminService.fetchAdminById(id);
        if(adminEntity.isPresent()){
            return ApiResponse.prepareApiResponse(adminEntity);
        }
        return ApiResponse.prepareFailureApiResponse("Admin Id not found!");
    }


}
