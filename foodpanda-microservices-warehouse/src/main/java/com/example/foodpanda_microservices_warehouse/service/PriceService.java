package com.example.foodpanda_microservices_warehouse.service;

import com.example.foodpanda_microservices_warehouse.dto.request.AddPriceRequest;
import com.example.foodpanda_microservices_warehouse.dto.request.DishPriceRequestV1;
import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;

public interface PriceService {

     ApiResponse fetchPrice();
     ApiResponse addPrice(AddPriceRequest request);
     ApiResponse fetchPriceByDish(String dish);
     ApiResponse fetchPriceByDishV1(DishPriceRequestV1 requestV1);
}
