package com.example.foodpanda_microservices_Mangement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse {


    private Integer id;
    private Double price;
    private String dish;

}
