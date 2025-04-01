package com.example.foodpanda_microservices.repository;

import com.example.foodpanda_microservices.dto.entities.OrderInvoiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInvoiceJpaRepository extends JpaRepository<OrderInvoiceInfo,Long> {

}
