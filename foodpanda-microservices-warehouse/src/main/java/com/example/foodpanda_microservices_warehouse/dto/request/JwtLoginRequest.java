package com.example.foodpanda_microservices_warehouse.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtLoginRequest {

    private String email;
    private String password;
}
