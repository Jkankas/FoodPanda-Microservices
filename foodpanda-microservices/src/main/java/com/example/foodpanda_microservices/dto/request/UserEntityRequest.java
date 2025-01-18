package com.example.foodpanda_microservices.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityRequest {


   @NotBlank(message = "Username cannot be Empty/Null")
   private String username;
   @NotBlank(message = "Password cannot be Empty/Null")
   private String password;
   private String department;
   @NotBlank(message = "Email Cannot be Null/Empty")
   private String email;
   private String role;

}
