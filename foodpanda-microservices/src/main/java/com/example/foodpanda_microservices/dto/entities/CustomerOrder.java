package com.example.foodpanda_microservices.dto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customer_order")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private String dish;
    private Double price;
    private int quantity;
    private LocalDateTime orderedAt;
    private String orderStatus;


    @ManyToOne()
    @JoinColumn(name="customer_id",referencedColumnName = "customer_id")
    @JsonIgnore
    private CustomerEntity customerEntity;

}
