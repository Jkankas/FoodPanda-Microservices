package com.example.foodpanda_microservices.repository;

import com.example.foodpanda_microservices.dto.entities.CustomerOrder;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderJpaRepository extends JpaRepository<CustomerOrder,Long> {

    @Query(value = "select * from customer_order where customer_id=:id order by ordered_at desc limit 1",nativeQuery = true)
    Optional<CustomerOrder> orderByCustomerId(@Param("id") String id);

    @Query(value = "select * from customer_order where order_id=:orderId",nativeQuery = true)
    Optional<CustomerOrder> getOrderDetails(@Param("orderId") Long orderId);

    @Query(value = "select * from customer_order where order_id in (:orderIds)",nativeQuery = true)
    Optional<List<CustomerOrder>> getBulkOrderDetails(@Param("orderIds") List<Long> orderIds);

    @Modifying
    @Transactional
    @Query(value = "update customer_order set order_status=:status where order_id=:id",nativeQuery = true)
    Optional<Integer> updateOrderStatus(@Param("id") Long id , @Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "update customer_order set order_status=:status where order_id in (:orderIds)",nativeQuery = true)
    Optional<Integer> updateBulkOrderStatus(@Param("orderIds") List<Long> orderIds,@Param("status") String status);



}

