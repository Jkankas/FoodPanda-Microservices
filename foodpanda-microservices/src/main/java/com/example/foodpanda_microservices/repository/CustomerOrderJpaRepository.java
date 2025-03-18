package com.example.foodpanda_microservices.repository;

import com.example.foodpanda_microservices.dto.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CustomerOrderJpaRepository extends JpaRepository<CustomerOrder,Long> {

    @Query(value = "select * from customer_order where customer_id=:id order by ordered_at desc limit 1",nativeQuery = true)
    Optional<CustomerOrder> orderByCustomerId(@Param("id") String id);

    @Query(value = "select * from customer_order where order_id=:orderId",nativeQuery = true)
    Optional<CustomerOrder> getOrderDetails(@Param("orderId") Long orderId);

    @Modifying
    @Transactional
    @Query(value = "update customer_order set order_status=:status where order_id=:id",nativeQuery = true)
    Optional<Integer> updateOrderStatus(@Param("id") Long id , @Param("status") String status);

}

