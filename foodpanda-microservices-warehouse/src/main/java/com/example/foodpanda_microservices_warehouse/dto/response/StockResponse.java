package com.example.foodpanda_microservices_warehouse.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StockResponse {
    private List<Map<String,Object>> fetchStock;
}

