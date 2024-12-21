package com.example.foodpanda_microservices_Mangement.controller.Rest;

import com.example.foodpanda_microservices_Mangement.dto.request.PriceRequest;
import com.example.foodpanda_microservices_Mangement.dto.response.ApiResponse;
import com.example.foodpanda_microservices_Mangement.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/price")
public class PriceController {


    @Autowired
    PriceService service;


    @PostMapping("/addPrice")
    public ResponseEntity<ApiResponse> addPrice( @RequestBody PriceRequest request){
        int result = service.addPrice(request);
        if(result==-1){
            return new ResponseEntity<>(ApiResponse.prepareFailApiResponse(result),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ApiResponse.prepareApiResponse(result),HttpStatus.OK);
    }


}
