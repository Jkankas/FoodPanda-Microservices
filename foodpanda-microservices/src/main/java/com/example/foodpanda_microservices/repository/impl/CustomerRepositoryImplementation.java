package com.example.foodpanda_microservices.repository.impl;

import com.example.foodpanda_microservices.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CustomerRepositoryImplementation implements CustomerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public Map<String,Object> searchCustomId(String phone){
      return   jdbcTemplate.queryForMap(SEARCH_CUSTOMER_ID,phone);
    }


    public Map<String,Object> searchCustomerDetails(String id){
        return   jdbcTemplate.queryForMap(SEARCH_CUSTOMER_BY_ID,id);
    }
}
