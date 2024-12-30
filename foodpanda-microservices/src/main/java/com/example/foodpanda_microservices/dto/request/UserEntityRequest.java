package com.example.foodpanda_microservices.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityRequest {

   private String username;
   private String password;
   private String department;
   private String email;
   private String role;

}
