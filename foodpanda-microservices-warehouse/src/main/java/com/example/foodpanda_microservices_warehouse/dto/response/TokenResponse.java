package com.example.foodpanda_microservices_warehouse.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    String token;
}
