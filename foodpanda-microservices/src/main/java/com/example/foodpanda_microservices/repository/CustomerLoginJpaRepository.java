package com.example.foodpanda_microservices.repository;

import com.example.foodpanda_microservices.dto.entities.CustomerLoginMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerLoginJpaRepository extends JpaRepository<CustomerLoginMetadata,Long> {

    @Query(value = "select * from customer_login_metadata where phone_number=:number  order by login_at desc limit 1",nativeQuery = true)
    Optional<CustomerLoginMetadata> findByMobile( @Param("number") String number);
}
