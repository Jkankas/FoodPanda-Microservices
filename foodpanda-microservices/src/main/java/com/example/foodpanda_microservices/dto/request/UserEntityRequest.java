package com.example.foodpanda_microservices.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityRequest {

    String username;
    String password;
    String department;
    String email;

}
