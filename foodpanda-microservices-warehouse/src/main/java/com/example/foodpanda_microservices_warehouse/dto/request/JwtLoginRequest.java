package com.example.foodpanda_microservices_warehouse.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtLoginRequest {

    @NotBlank(message = "email cannot be blank/Empty")
    private String email;
    @NotBlank(message = "Password cannot be blank/Empty")
    private String password;
}
