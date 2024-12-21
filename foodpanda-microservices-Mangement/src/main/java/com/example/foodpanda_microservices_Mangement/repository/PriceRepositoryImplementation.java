package com.example.foodpanda_microservices_Mangement.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PriceRepositoryImplementation {


    @Autowired
    JdbcTemplate template;


    public int addPrice(Double price , String dish){
        String sql = PriceRepository.ADD_PRICE;
        return template.update(sql,price,dish);
    }

}
