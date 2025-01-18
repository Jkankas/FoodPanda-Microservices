package com.example.foodpanda_microservices.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {

    @NotBlank(message = "Dish Cannot be Null/Empty")
    private String dish;
    @NotBlank(message = "Category Cannot be Null/Empty")
    private String category;
}
