package com.example.foodpanda_microservices_warehouse.repository.impl;

import com.example.foodpanda_microservices_warehouse.dto.request.UpdateStockRequestV1;
import com.example.foodpanda_microservices_warehouse.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StockRepositoryImplementation implements StockRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void addStock(int stock,String dish){
        String sql = StockRepository.ADD_STOCK;
        jdbc.update(sql,stock,dish);
    }

    public List<Map<String,Object>> fetchStock(){
        return jdbc.queryForList(StockRepository.FETCH_STOCK);
    }

    public Map<String,Object> fetchStockByDish(String dish){
        return jdbc.queryForMap(FETCH_STOCK_BY_DISH,dish);
    }





    public List<Map<String,Object>> fetchStockByDishV1(List<String> dish){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        StringBuilder fetchDishes = new StringBuilder(FETCH_STOCK_BY_DISH_V1);
        if(!ObjectUtils.isEmpty(dish)){
            fetchDishes.append(" AND dish in (:dish)");
            parameterSource.addValue("dish",dish);
        }
       return namedParameterJdbcTemplate.queryForList(fetchDishes.toString(),parameterSource);
    }





    @Override
    public int updateStock(int stock,String dish) {
        return jdbc.update(UPDATE_STOCK,stock,dish);
    }



    @Override
    public int updateStockV1(UpdateStockRequestV1 requestV1) {

    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    StringBuilder updateStocks = new StringBuilder(UPDATE_STOCK_V1);

    Map<String,Integer> dishStockMap = new HashMap<>();
    for(int i=0;i<requestV1.getStocks().size();i++){
        dishStockMap.put(requestV1.getDishes().get(i),requestV1.getStocks().get(i));
    }
    for(Map.Entry<String,Integer> entries : dishStockMap.entrySet()){
        parameterSource.addValue("stock",entries.getValue());
        parameterSource.addValue("dish",entries.getKey());
        namedParameterJdbcTemplate.update(updateStocks.toString(),parameterSource);
    }

//    updateStocks.append("WHEN dish = :dish1 THEN :stock1").append(" WHEN dish = :dish2 THEN :stock2")
//            .append(" ELSE stock END").append(" WHERE dish in (:dishes)");
//    parameterSource.addValue("stock1",requestV1.getStocks().get(0));
//    parameterSource.addValue("dish1",requestV1.getDishes().get(0));
//    parameterSource.addValue("stock2",requestV1.getStocks().get(1));
//        parameterSource.addValue("dish2",requestV1.getDishes().get(1));
//    parameterSource.addValue("dishes",requestV1.getDishes());

//        return namedParameterJdbcTemplate.update(updateStocks.toString(),parameterSource);
            return 1;
    }

}
