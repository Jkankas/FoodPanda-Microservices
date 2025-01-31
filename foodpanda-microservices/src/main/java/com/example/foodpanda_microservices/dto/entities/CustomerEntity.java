package com.example.foodpanda_microservices.dto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
@Entity
@Table(name = "customer_profile")
public class CustomerEntity {

    private long id;
    @Id
    private String customerId;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String landmark;
    private String pin;
    private String city;
    private String state;
    private LocalDateTime created_at;
}
