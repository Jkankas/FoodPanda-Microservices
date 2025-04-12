package com.example.foodpanda_microservices_warehouse.repository;

import java.util.List;
import java.util.Map;

public interface PriceRepository {

    String ADD_PRICE = "insert into dish_price (dish,price) values (?,?)";
    String FETCH_PRICE = "select * from dish_price";
    String FETCH_PRICE_BY_DISH = "select dish,price from dish_price where dish = ?";
    String FETCH_PRICE_BY_DISH_V1 = "select dish,price from dish_price where dish IN (:dishes) ";



     void addPrice(String dish, Double price);
     List<Map<String,Object>> fetchPrice();
     Map<String,Object> fetchPriceByDish(String dish);
     List<Map<String,Object>> fetchPriceByDishV1(List<String> dishes);
}
