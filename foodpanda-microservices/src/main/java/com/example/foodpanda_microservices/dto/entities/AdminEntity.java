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

    @Id
    private String adminId;
    private long id;
    private String fullName;
    private String addressLine1;
    private String addressLine2;
    private int pin;
    private String city;
    private String State;
    @JsonIgnore
    private String password;
    private String department;
    private String email;
    private String role;
    @JsonProperty("is_active")
    @Column(name = "is_active")
    private boolean isActive;
    @Builder.Default
    @JsonIgnore
    private LocalDateTime updated_at = LocalDateTime.now();
}
