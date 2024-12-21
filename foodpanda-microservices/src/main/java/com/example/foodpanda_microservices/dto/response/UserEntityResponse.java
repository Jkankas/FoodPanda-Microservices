package com.example.foodpanda_microservices.dto.response;

import lombok.Data;

@Data
public class UserEntityResponse {
    private String id;
    private String userName;
    private String password;
    private String department;
    private String email;
}
