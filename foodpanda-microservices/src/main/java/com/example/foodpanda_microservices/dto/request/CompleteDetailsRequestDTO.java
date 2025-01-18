package com.example.foodpanda_microservices.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteDetailsRequestDTO {


  @NotBlank(message = "Dish Cannot be empty/Null")
  private String dish;
}
