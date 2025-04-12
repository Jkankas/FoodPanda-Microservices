package com.example.foodpanda_microservices_warehouse.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestV1 {

    private List<String> dishes ;
}
