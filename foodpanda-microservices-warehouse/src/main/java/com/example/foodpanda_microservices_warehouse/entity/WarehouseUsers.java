package com.example.foodpanda_microservices_warehouse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="warehouse_profiles")
public class WarehouseUsers {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String department;
    @JsonProperty("is_active")
    private boolean is_Active;

}
