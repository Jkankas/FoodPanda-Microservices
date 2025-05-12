package com.example.foodpanda_microservices.dto.entities;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_history")
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "invoice_no")
    private String invoiceNo;
    @Column(name = "utr")
    private String utr;
    // @Column(name = "order_id")
    // private String orderId;



    @OneToOne
    @JoinColumn(name = "order_id",referencedColumnName = "order_id")
    private CustomerOrder customerOrder;




}
