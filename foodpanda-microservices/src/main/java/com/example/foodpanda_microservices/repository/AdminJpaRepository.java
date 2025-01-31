package com.example.foodpanda_microservices.repository;

import com.example.foodpanda_microservices.dto.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminJpaRepository extends JpaRepository<AdminEntity,String> {


    Optional<AdminEntity> findById(String id);


}
