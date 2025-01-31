package com.example.foodpanda_microservices.dto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin_profiles")
public class AdminEntity {


    private long id;
    @Id
    private String adminId;
    @NotBlank(message = "Username cannot be Empty/Null")
    private String fullName;
    @NotBlank(message = "addressLine1 cannot be Empty/Null")
    private String addressLine1;
    private String addressLine2;
    @NotBlank(message = "pin cannot be Empty/Null")
    private int pin;
    private String city;
    private String State;
    @NotBlank(message = "Password cannot be Empty/Null")
    @JsonIgnore
    private String password;
    private String department;
    @NotBlank(message = "Email Cannot be Null/Empty")
    private String email;
    private String role;
    @JsonProperty("is_active")
    @Column(name = "is_active")
    private boolean isActive;
    @Builder.Default
    @JsonIgnore
    private LocalDateTime updated_at = LocalDateTime.now();
}
