package com.example.foodpanda_microservices_warehouse.repository.impl;


import com.example.foodpanda_microservices_warehouse.entity.Price;
import com.example.foodpanda_microservices_warehouse.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PriceRepositoryImplementation implements PriceRepository {


    @Autowired
    private JdbcTemplate template;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void addPrice(String dish, Double price){
        template.update(PriceRepository.ADD_PRICE,dish,price);
    }

    public List<Map<String,Object>> fetchPrice(){
        return template.queryForList(PriceRepository.FETCH_PRICE);
    }

    public Map<String,Object> fetchPriceByDish(String dish){
        return template.queryForMap(PriceRepository.FETCH_PRICE_BY_DISH,dish);
    }

    public List<Map<String,Object>> fetchPriceByDishV1(List<String> dishes){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("dishes",dishes);
        return namedParameterJdbcTemplate.queryForList(FETCH_PRICE_BY_DISH_V1,parameterSource);
    }

}
