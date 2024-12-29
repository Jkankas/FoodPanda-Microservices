package com.example.foodpanda_microservices_warehouse.repository;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserProfileRepository extends JpaRepository<WarehouseUsers,Long> {


    @Query("select c from WarehouseUsers c where c.email = ?1 and c.is_Active = true")
     Optional<WarehouseUsers> findByActiveEmail(String email);

}
