package com.example.foodpanda_microservices.dto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "order_invoice_info")
public class OrderInvoiceInfo {
    @Id
    private Long id;
    private String packageId;
    private String dish;
    private int quantity;
    private double price;
    private String invoiceNo;
    private boolean orderConfirmed;
}
