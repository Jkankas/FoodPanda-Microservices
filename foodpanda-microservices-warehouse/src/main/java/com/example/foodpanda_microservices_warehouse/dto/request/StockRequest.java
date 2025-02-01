package com.example.foodpanda_microservices_warehouse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockRequest {

    @NotNull(message = "stock cannot be empty or null")
    private Integer stock;
    @NotBlank(message = "Dish cannot be empty or null")
    private String dish;
}


