package com.example.foodpanda_microservices.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username cannot be Null/Empty")
    private String username;
    @NotBlank(message = "Password cannot be Null/Empty")
    private String password;
}
