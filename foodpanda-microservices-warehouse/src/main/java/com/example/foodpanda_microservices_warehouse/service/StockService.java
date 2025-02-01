package com.example.foodpanda_microservices_warehouse.service;

import com.example.foodpanda_microservices_warehouse.dto.request.StockRequest;
import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface StockService {


    public ResponseEntity<ApiResponse> addStock(StockRequest request);
    public ApiResponse fetchStock();
    public ApiResponse fetchStockByDish(String dish);
}
