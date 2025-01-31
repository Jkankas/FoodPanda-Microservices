package com.example.foodpanda_microservices.dto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_login_metadata")
public class CustomerLoginMetadata {

    @Id
    private String phoneNumber;
    private Integer otp;
    private LocalDateTime login_at;

}
