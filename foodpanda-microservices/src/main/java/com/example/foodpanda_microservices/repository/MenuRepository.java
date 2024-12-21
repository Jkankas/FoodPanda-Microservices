package com.example.foodpanda_microservices.repository;

import java.util.List;
import java.util.Map;

public interface MenuRepository {

    String ADD_MENU = "insert into menu (dish,category) values (?,?)";
    String FETCH_ALL= "select * from menu";
    String FETCH_USER_DETAILS = "select * from user_profile";
    String CREATE_USER = "insert into user_profile(username,password,department,email) values (?,?,?,?)";


    public int addMenu(String dish, String category);
    public List<Map<String,Object>> fetchAllMenu();
    public List<Map<String,Object>> userProfile();
    public void createProfile(String username,String password, String department,String email);

}
