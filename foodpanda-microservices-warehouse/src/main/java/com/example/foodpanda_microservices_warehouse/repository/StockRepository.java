package com.example.foodpanda_microservices_warehouse.repository;

import java.util.List;
import java.util.Map;

public interface StockRepository {

    String ADD_STOCK = "insert into warehouse_stock (stock,dish) values (?,?)";
    String FETCH_STOCK = "select * from warehouse_stock";
    String FETCH_STOCK_BY_DISH = "select dish,stock from warehouse_stock where dish =?";
    String UPDATE_STOCK = "update warehouse_stock set stock = ? where dish = ?";


    void addStock(int stock,String dish);
    List<Map<String,Object>> fetchStock();
    Map<String,Object> fetchStockByDish(String dish);
    int updateStock(int stock,String dish);


}
