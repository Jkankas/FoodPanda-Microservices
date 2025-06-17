package com.example.foodpanda_microservices.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AuditRepositoryImpl {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    String FETCH_ORDERS_DETAILS = "select order_id,order_status,ordered_at,price,customer_id from customer_order";
    String FETCH_MENU_DETAILS = "select * from menu_list";



    public List<Map<String,Object>> orderedItems(){
        String query = FETCH_ORDERS_DETAILS;
        return jdbcTemplate.queryForList(query);
    }


    public List<Map<String,Object>> ordersTotal(){
        String query = FETCH_ORDERS_DETAILS;
        return jdbcTemplate.queryForList(query);
    }


    public List<Map<String,Object>> distinctCategories(){
        return jdbcTemplate.queryForList(FETCH_MENU_DETAILS);
    }






}
