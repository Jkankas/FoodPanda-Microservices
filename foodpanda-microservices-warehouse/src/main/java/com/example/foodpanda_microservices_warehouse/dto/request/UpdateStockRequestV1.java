package com.example.foodpanda_microservices_warehouse.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStockRequestV1 {
  private List<String> dishes;
  private List<Integer> stocks;
}
