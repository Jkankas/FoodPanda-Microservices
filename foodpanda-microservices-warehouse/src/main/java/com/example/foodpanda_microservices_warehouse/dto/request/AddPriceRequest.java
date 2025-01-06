package com.example.foodpanda_microservices_warehouse.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddPriceRequest {



   @NotBlank(message = "dish cannot be empty/null")
   private String dish;

   @NotBlank(message = "dish cannot be empty/null")
   private Double price;
}
