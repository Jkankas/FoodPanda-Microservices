package com.example.foodpanda_microservices.repository.impl;

import com.example.foodpanda_microservices.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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



}
