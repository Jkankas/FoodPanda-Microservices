package com.example.foodpanda_microservices.repository;

import com.example.foodpanda_microservices.dto.entities.CustomerEntity;
import com.example.foodpanda_microservices.dto.entities.CustomerLoginMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerProfileJpaRepository extends JpaRepository<CustomerEntity,String> {

    @Query(value = "select * from customer_profile where phone_number=:number",nativeQuery = true)
    Optional<CustomerEntity> findByMobile(@Param("number") String number);

}
