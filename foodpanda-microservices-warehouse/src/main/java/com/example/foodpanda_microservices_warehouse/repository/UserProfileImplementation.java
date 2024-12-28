package com.example.foodpanda_microservices_warehouse.repository;

import com.example.foodpanda_microservices_warehouse.entity.WarehouseUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Repository
public class UserProfileImplementation implements UserProfileReadRepository {

    @Autowired
    JdbcTemplate template;

    public Map<String,Object> findUserByEmail(String email) {
//        WarehouseUsers users = null;
//
//      final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//      try{
//          Map<String,Object> userFromDB = template.queryForMap(FIND_USER_BY_EMAIL,email);
//          users = new WarehouseUsers();
//          users.setId((Long)userFromDB.get("id"));
//          users.setUsername((String)userFromDB.get("username"));
//          users.setPassword((String)userFromDB.get("password"));
//          users.setDepartment((String)userFromDB.get("department"));
//          users.setEmail((String)userFromDB.get("email"));
////        users.setPassword(passwordEncoder.encode((String)userFromDB.get("password")));
//          return users;
//      } catch (Exception e){
//          System.out.println("User not found!");
//      }
//        return users;
//    }
        Map<String, Object> userFromDB = template.queryForMap(FIND_USER_BY_EMAIL, email);
        return userFromDB;

    }

}