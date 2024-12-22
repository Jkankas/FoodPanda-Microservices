package com.example.foodpanda_microservices_warehouse.repository;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

public class UserProfileImplementation implements UserProfileReadRepository{

    @Autowired
    JdbcTemplate template;

    public WarehouseUsers findUserByEmail(String email){
        WarehouseUsers users = null;

        Map<String,Object> userFromDB = template.queryForMap(FIND_USER_BY_EMAIL,email);
        users = new WarehouseUsers();
        users.setId((Long)userFromDB.get("id"));
        users.setUsername((String)userFromDB.get("username"));
        users.setPassword((String)userFromDB.get("password"));
        users.setDepartment((String)userFromDB.get("department"));
        users.setEmail((String)userFromDB.get("email"));

        return users;
    }
}
