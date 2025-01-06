package com.example.foodpanda_microservices_warehouse.repository.impl;


import com.example.foodpanda_microservices_warehouse.entity.Price;
import com.example.foodpanda_microservices_warehouse.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PriceRepositoryImplementation implements PriceRepository {


    @Autowired
    JdbcTemplate template;


    public void addPrice(String dish, Double price){
        template.update(PriceRepository.ADD_PRICE,dish,price);
    }



    public List<Map<String,Object>> fetchPrice(){
        return template.queryForList(PriceRepository.FETCH_PRICE);
    }




}
