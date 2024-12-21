package com.example.foodpanda_microservices_warehouse.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockRequest {

    private Integer stock;
    private String dish;



}
