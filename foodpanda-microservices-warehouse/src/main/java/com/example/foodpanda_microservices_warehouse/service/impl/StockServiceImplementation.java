package com.example.foodpanda_microservices_warehouse.service.impl;

import com.example.foodpanda_microservices_warehouse.configuration.ApplicationProperties;
import com.example.foodpanda_microservices_warehouse.dto.request.StockRequest;
import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import com.example.foodpanda_microservices_warehouse.dto.response.MenuListResponse;
import com.example.foodpanda_microservices_warehouse.dto.response.StockResponse;
import com.example.foodpanda_microservices_warehouse.repository.StockRepository;
import com.example.foodpanda_microservices_warehouse.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;




@Service
public class StockServiceImplementation implements StockService {

    @Autowired
    StockRepository repository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ApplicationProperties properties;


    @Override
    public ResponseEntity<ApiResponse> addStock(StockRequest request) {
//        LoginRequestBody requestBody = new LoginRequestBody();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.valueOf("application/json"));
//        HttpEntity<LoginRequestBody> httpEntity = new HttpEntity<>(requestBody, headers);
//        ResponseEntity<TokenResponse> token = restTemplate.exchange(properties.getToken(),
//                HttpMethod.POST, httpEntity, TokenResponse.class);

//        HttpHeaders headers1 = new HttpHeaders();
//        headers1.set("Authorization", "Bearer " + token.getBody().getToken());
//        HttpEntity<?> httpEntity1 = new HttpEntity<>(headers1);


        ResponseEntity<MenuListResponse> menuList = restTemplate.exchange(properties.getDish_list(),
                HttpMethod.GET, null, MenuListResponse.class);


        Map<String, Object> map = (Map<String, Object>) menuList.getBody().getResult();
        List result = (List) map.get("menuList");

        if (ObjectUtils.isEmpty(result)) {
            return new ResponseEntity<>(
                    ApiResponse.builder().result(null).message("Menu List is Empty/null").Status(500).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        List<Map<String, Object>> list = (List<Map<String, Object>>) result;
        for (Map<String, Object> map1 : list) {
            if (map1.get("dish").equals(request.getDish())) {
                repository.addStock(request.getStock(), request.getDish());
                return new ResponseEntity<>(ApiResponse.prepareApiResponse("Stock Succesfully Updated"),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(ApiResponse.prepareApiResponse("Dish Currenlty not " +
                "available in the Menu"), HttpStatus.BAD_REQUEST);
    }




            // fetch Stock
    public ApiResponse fetchStock(){
        StockResponse response = new StockResponse();
        List<Map<String,Object>> list = repository.fetchStock();
        response.setFetchStock(list);
        return ApiResponse.prepareApiResponse(response.getFetchStock());
    }




    public ApiResponse fetchStockByDish(String dish){
        Map<String,Object> map = repository.fetchStockByDish(dish);
        return ApiResponse.prepareApiResponse(map);
    }


    public ApiResponse updateStock(String dish){

        try{
            Map<String,Object> originalStockMap = repository.fetchStockByDish(dish);
        }catch (Exception ex){
           throw ex;
        }





       int updatedStock = repository.updateStock(dish);
       if(updatedStock==0){
           throw new IllegalStateException("Error while Updating Stocks!");
       }




       return ApiResponse.prepareApiResponse("Succesfully updated");
    }
}
