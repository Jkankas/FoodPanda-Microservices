package com.example.foodpanda_microservices_Mangement.dto.request;

import lombok.Data;

@Data
public class PriceRequest {

    private Double price;
    private String dish;

}
