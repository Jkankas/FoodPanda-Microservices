package com.example.foodpanda_microservices.dto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin_profiles")
public class UserEntity {

    @Id
    private Long id;
    private String username;
    private String password;
    private String department;
    private String email;
    private String role;


}
