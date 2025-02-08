package com.example.foodpanda_microservices_warehouse.controller.Rest;

import com.example.foodpanda_microservices_warehouse.dto.request.AddPriceRequest;
import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import com.example.foodpanda_microservices_warehouse.service.PriceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse")
public class PriceController {

    @Autowired
    private PriceService service;

    @Autowired
    private Logger log;

    @GetMapping("/getPriceList")
    public ApiResponse fetchPrice(){
        log.info("Into fetch Price List Controller");
        return service.fetchPrice();
    }


    @PostMapping("/addPrice")
    public ApiResponse addPrice( @Valid @RequestBody AddPriceRequest request){
        log.info("request received at Add Price Controller,{}",request);
        return service.addPrice(request);
    }


    @GetMapping("/getPriceByDish/{dish}")
    public ApiResponse fetchPriceByDish(@PathVariable String dish){
        log.info("request received at getPriceByDish Controller,{}",dish);
        return service.fetchPriceByDish(dish);
    }
}
