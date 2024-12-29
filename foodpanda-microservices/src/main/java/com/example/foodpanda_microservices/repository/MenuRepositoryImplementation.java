package com.example.foodpanda_microservices.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MenuRepositoryImplementation implements MenuRepository {

    @Autowired
   private JdbcTemplate template;


    public int addMenu(String dish, String category){
        String sql = MenuRepository.ADD_MENU;
        return template.update(sql,dish,category);
    }


    public List<Map<String,Object>> fetchAllMenu(){
        String sql = MenuRepository.FETCH_ALL;
        return template.queryForList(sql);
    }


    public  Map<String, Object> userProfile(String email){
        return template.queryForMap(MenuRepository.FETCH_USER_DETAILS,email);
    }


    public void createProfile(String username, String password , String department ,String email){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPass = passwordEncoder.encode(password);
        template.update(MenuRepository.CREATE_USER,username,encryptedPass,department,email);
    }



}
