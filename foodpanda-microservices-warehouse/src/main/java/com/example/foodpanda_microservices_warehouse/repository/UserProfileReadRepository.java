package com.example.foodpanda_microservices_warehouse.repository;


import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;

import java.util.Map;


public interface UserProfileReadRepository {

    String FIND_USER_BY_EMAIL= "select * from warehouse_profiles where email = ?";

    Map<String,Object> findUserByEmail(String email);

}
