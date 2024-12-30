package com.example.foodpanda_microservices.repository;

import java.util.List;
import java.util.Map;

public interface MenuRepository {

    String ADD_MENU = "insert into menu_list (dish,category) values (?,?)";
    String FETCH_ALL= "select * from menu_list";
    String FETCH_USER_DETAILS = "select * from admin_profiles where email = ?";
    String CREATE_USER = "insert into admin_profiles(department,email,password,username,role) values (?,?,?,?,?)";


    int addMenu(String dish, String category);
     List<Map<String,Object>> fetchAllMenu();
     Map<String,Object> userProfile(String email);
     void createProfile(String department, String email , String password ,String username,String role);

}
