package com.example.foodpanda_microservices_warehouse.repository;

import java.util.List;
import java.util.Map;

public interface StockRepository {

    String ADD_STOCK = "insert into menu_stock (stock,dish) values (?,?)";
    String FETCH_STOCK = "select * from menu_stock";


    public void addStock(int stock,String dish);
    public List<Map<String,Object>> fetchStock();

}