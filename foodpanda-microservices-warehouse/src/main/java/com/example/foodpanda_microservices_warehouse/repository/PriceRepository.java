package com.example.foodpanda_microservices_warehouse.repository;

import java.util.List;
import java.util.Map;

public interface PriceRepository {

    String ADD_PRICE = "insert into dish_price (dish,price) values (?,?)";
    String FETCH_PRICE = "select * from dish_price";



     void addPrice(String dish, Double price);
     List<Map<String,Object>> fetchPrice();
}
