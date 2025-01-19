package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.dto.entities.Menu;
import com.example.foodpanda_microservices.dto.request.CompleteDetailsRequestDTO;
import com.example.foodpanda_microservices.dto.request.MenuRequest;
import com.example.foodpanda_microservices.dto.response.*;
import com.example.foodpanda_microservices.repository.MenuJpaRepository;
import com.example.foodpanda_microservices.repository.MenuRepository;
import com.example.foodpanda_microservices.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class MenuServiceImplementation implements MenuService {

     @Autowired
    MenuRepository repository;

     @Autowired
     MenuJpaRepository menuJpaRepository;

     @Autowired
     RestTemplate restTemplate;


    @Override
    public int addMenu(MenuRequest request) {
        return repository.addMenu(request.getDish(),request.getCategory());
    }



    @Override
    public FetchAllMenuResponse fetchAllMenuList() {
        List<Map<String,Object>> list = repository.fetchAllMenu();
        FetchAllMenuResponse menuResponse =  FetchAllMenuResponse.builder().build();

        for(Map<String,Object> map : list){
            MenuResponse response = new MenuResponse();
            response.setId((Long) map.get("dish_id"));
            response.setDish((String)map.get("dish"));
            response.setCategory((String)map.get("category"));
            menuResponse.getMenuList().add(response);
        }
        return menuResponse;
    }



   public Optional<Menu> fetchMenuDetails(String dish){
        CompleteMenuDetailsResponseDTO responseDTO = new CompleteMenuDetailsResponseDTO();
       Optional<Menu> menuList = menuJpaRepository.findMenuDetails(dish);
       if(menuList.isPresent()){
           Menu menu = menuList.get();
           responseDTO.setDish_id(menu.getDishId());
           responseDTO.setCategory(menu.getCategory());
           responseDTO.setDish(menu.getDish());
       }
       else{
           System.out.println(menuList);
           return Optional.empty();

       }
        return menuList;
    }



    public ApiResponse fetchCompleteDishDetails(String dish){

        Optional<Menu> menu =  fetchMenuDetails(dish);
        if(menu.isEmpty()){
            return ApiResponse.prepareFailureApiResponse("Dish not Available!");
        }

        Integer stockValue = 0;
        Double price = 0.0;
        String stockUri = "http://localhost:8081/api/warehouse/fetchStockByDish/{dish}";
        StockResponse response = new StockResponse();
       response= restTemplate.getForObject(stockUri,StockResponse.class,dish);
       Map<String,Object> stockMap = new HashMap<>();
       stockMap = (Map<String,Object>) response.getResult();
        String dishName = (String)stockMap.get("dish");
       if(dishName.equalsIgnoreCase(dish)){
          stockValue = (Integer) stockMap.get("stock");
       }


       String priceUri = "http://localhost:8081/api/warehouse/getPriceByDish/{dish}";
       PriceResponse response1 = new PriceResponse();
       response1 = restTemplate.getForObject(priceUri,PriceResponse.class,dish);
        Map<String,Object> priceMap = new HashMap<>();
        priceMap = (Map<String,Object>) response1.getResult();
        String result1 = (String)priceMap.get("dish");
        if(result1.equalsIgnoreCase(dish)){
             price = (Double) priceMap.get("price");
        }


   CompleteMenuDetailsResponseDTO responseDTO1=  CompleteMenuDetailsResponseDTO.builder().dish_id(menu.get().getDishId()).dish(menu.get().getDish()).
           Category(menu.get().getCategory()).stock(stockValue).price(price)
                .build();

        return ApiResponse.prepareApiResponse(responseDTO1);

    }

}
