package com.example.foodpanda_microservices.repository;

import com.example.foodpanda_microservices.dto.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileJpaRepository extends JpaRepository<AdminEntity,Long> {


}
