package com.example.foodpanda_microservices.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MenuRepository {

    String ADD_MENU = "insert into menu_list (dish,category) values (?,?)";
    String FETCH_ALL= "select * from menu_list";
    String FETCH_USER_DETAILS = "select * from admin_profiles where email = ?";
    String CREATE_USER = "insert into admin_profiles(department,password,role,full_name,email,pin,address_line1,address_line2,updated_at,is_active, city, state,admin_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";


     int addMenu(String dish, String category);
     List<Map<String,Object>> fetchAllMenu();
     Map<String,Object> userProfile(String email);
    void createProfile(String department, String password, String role, String fullName, String email, int pin, String address1, String address2, LocalDateTime date, boolean is_active,String state,String city,String adminId);
}
