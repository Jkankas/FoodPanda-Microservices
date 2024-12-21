package com.example.foodpanda_microservices_warehouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="warehouse_profiles")
public class WarehouseUsers {


    @Id
    private String id;

    @Column(name = "user_name")
    private String username;
    private String password;
    private String email;
    private String department;
}
