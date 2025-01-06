package com.example.foodpanda_microservices_warehouse.controller.Rest;

import com.example.foodpanda_microservices_warehouse.dto.request.AddPriceRequest;
import com.example.foodpanda_microservices_warehouse.dto.response.AddPriceResponse;
import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import com.example.foodpanda_microservices_warehouse.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse")
public class PriceController {

    @Autowired
    PriceService service;


    @GetMapping("/getPriceList")
    public ApiResponse fetchPrice(){
        return service.fetchPrice();
    }


    @PostMapping("/addPrice")
    public ApiResponse addPrice( @RequestBody AddPriceRequest request){
        return service.addPrice(request);
    }
    
}
