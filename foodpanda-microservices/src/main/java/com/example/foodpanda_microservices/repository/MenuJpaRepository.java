package com.example.foodpanda_microservices.repository;

import com.example.foodpanda_microservices.dto.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MenuJpaRepository extends JpaRepository<Menu,Long> {

    @Query(value = "select * from menu_list where dish = ?1",nativeQuery = true)
    Optional<Menu> findMenuDetails(String dish);



}
