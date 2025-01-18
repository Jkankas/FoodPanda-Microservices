package com.example.foodpanda_microservices.repository;

import com.example.foodpanda_microservices.dto.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileJpaRepository extends JpaRepository<UserEntity,Long> {


}
