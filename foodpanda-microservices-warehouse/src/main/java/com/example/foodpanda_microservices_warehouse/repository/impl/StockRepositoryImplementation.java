package com.example.foodpanda_microservices_warehouse.repository.impl;

import com.example.foodpanda_microservices_warehouse.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class StockRepositoryImplementation implements StockRepository {

    @Autowired
    JdbcTemplate jdbc;


    public void addStock(int stock,String dish){
        String sql = StockRepository.ADD_STOCK;
        jdbc.update(sql,stock,dish);
    }

    public List<Map<String,Object>> fetchStock(){
        return jdbc.queryForList(StockRepository.FETCH_STOCK);
    }


}
