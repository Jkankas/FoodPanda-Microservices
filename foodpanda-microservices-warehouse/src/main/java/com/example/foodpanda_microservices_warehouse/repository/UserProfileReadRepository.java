package com.example.foodpanda_microservices_warehouse.repository;


import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;


public interface UserProfileReadRepository {

    String FIND_USER_BY_EMAIL= "select * from warehouse_profiles where email = ?";

     WarehouseUsers findUserByEmail(String email);

}
