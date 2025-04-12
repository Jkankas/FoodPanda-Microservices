package com.example.foodpanda_microservices_warehouse.service;

import com.example.foodpanda_microservices_warehouse.dto.request.StockRequest;
import com.example.foodpanda_microservices_warehouse.dto.request.UpdateStockRequestV1;
import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface StockService {


    ResponseEntity<ApiResponse> addStock(StockRequest request);
    ApiResponse fetchStock();
    ApiResponse fetchStockByDish(String dish);
    ApiResponse fetchStockByDishV1(List<String> dish);
    ApiResponse updateStock(int stock,String dish);
    ApiResponse updateStockV1(UpdateStockRequestV1 requestV1);


}
