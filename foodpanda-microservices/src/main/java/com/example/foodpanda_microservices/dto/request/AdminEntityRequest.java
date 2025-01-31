package com.example.foodpanda_microservices.dto.request;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminEntityRequest {


   @NotBlank(message = "fullName cannot be Empty/Null")
   private String fullName;
   @NotBlank(message = "addressLine1 cannot be Empty/Null")
   private String addressLine1;
   private String addressLine2;
   @NotNull(message = "pin cannot be Empty/Null")
   private Integer pin;
   private String city;
   private String State;
   @NotBlank(message = "Password cannot be Empty/Null")
   private String password;
   private String department;
   @NotBlank(message = "Email Cannot be Null/Empty")
   private String email;
   private String role;
   @JsonProperty("is_active")
   @Column(name = "is_active")
   private boolean isActive;
   private LocalDateTime updated_at;
}
