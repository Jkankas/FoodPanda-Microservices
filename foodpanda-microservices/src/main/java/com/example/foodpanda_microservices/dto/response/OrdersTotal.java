package com.example.foodpanda_microservices.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersTotal {

    private long id;
    private String orderStatus;
    private LocalDateTime orderedAt;
    private double price;
    private String customerId;

}
