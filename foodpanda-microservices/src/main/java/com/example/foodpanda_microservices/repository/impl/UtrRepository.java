package com.example.foodpanda_microservices.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.foodpanda_microservices.dto.request.PaymentUtrRequest;
import com.example.foodpanda_microservices.repository.UtrJdbcRepository;

@Repository
public class UtrRepository implements UtrJdbcRepository {


    @Autowired
    JdbcTemplate jdbcTemplate;



    public void updateUtr(PaymentUtrRequest request){
        jdbcTemplate.update(UtrJdbcRepository.UPDATE_UTR,request.getUtrNumber(),44);
    }


}
