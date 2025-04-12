package com.example.foodpanda_microservices_warehouse.repository;

import com.example.foodpanda_microservices_warehouse.dto.request.UpdateStockRequestV1;

import java.util.List;
import java.util.Map;

public interface StockRepository {

    String ADD_STOCK = "insert into warehouse_stock (stock,dish) values (?,?)";
    String FETCH_STOCK = "select * from warehouse_stock";
    String FETCH_STOCK_BY_DISH = "select dish,stock from warehouse_stock where dish =?";
    String FETCH_STOCK_BY_DISH_V1 = "select dish,stock from warehouse_stock where 1=1";
    String UPDATE_STOCK = "update warehouse_stock set stock = ? where dish = ?";
    String UPDATE_STOCK_V1 = "update warehouse_stock set stock = :stock where dish = :dish ";


    void addStock(int stock,String dish);
    List<Map<String,Object>> fetchStock();
    Map<String,Object> fetchStockByDish(String dish);
    List<Map<String,Object>> fetchStockByDishV1(List<String> dish);
    int updateStock(int stock,String dish);
    int updateStockV1(UpdateStockRequestV1 requestV1);


}
