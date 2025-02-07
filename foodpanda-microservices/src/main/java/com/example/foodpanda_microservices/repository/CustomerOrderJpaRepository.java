package com.example.foodpanda_microservices.repository;

import com.example.foodpanda_microservices.dto.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerOrderJpaRepository extends JpaRepository<CustomerOrder,Long> {

    @Query(value = "select * from customer_order where customer_id=:id order by ordered_at desc limit 1",nativeQuery = true)
    Optional<CustomerOrder> orderByCustomerId(@Param("id") String id);
}

