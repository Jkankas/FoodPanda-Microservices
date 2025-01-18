package com.example.foodpanda_microservices.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteMenuDetailsResponseDTO {

    private Long dish_id;
    private String Category;
    private String dish;
    private Double price;
    private int stock;

}
