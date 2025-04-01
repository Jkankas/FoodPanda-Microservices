package com.example.foodpanda_microservices.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderRequest {

    private String dish;
    private Integer quantity;
    private String orderStatus;

}
