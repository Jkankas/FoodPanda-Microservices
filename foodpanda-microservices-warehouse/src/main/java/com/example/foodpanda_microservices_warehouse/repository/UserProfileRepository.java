package com.example.foodpanda_microservices_warehouse.repository;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface UserProfileRepository extends JpaRepository<WarehouseUsers,Long> {


}
