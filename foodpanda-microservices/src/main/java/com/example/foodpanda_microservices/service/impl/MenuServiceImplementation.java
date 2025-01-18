package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.dto.entities.Menu;
import com.example.foodpanda_microservices.dto.request.CompleteDetailsRequestDTO;
import com.example.foodpanda_microservices.dto.request.MenuRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.CompleteMenuDetailsResponseDTO;
import com.example.foodpanda_microservices.dto.response.FetchAllMenuResponse;
import com.example.foodpanda_microservices.dto.response.MenuResponse;
import com.example.foodpanda_microservices.repository.MenuJpaRepository;
import com.example.foodpanda_microservices.repository.MenuRepository;
import com.example.foodpanda_microservices.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;


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



   public Optional<Menu> fetchMenuDetails(CompleteDetailsRequestDTO requestDTO){
        CompleteMenuDetailsResponseDTO responseDTO = new CompleteMenuDetailsResponseDTO();
       Optional<Menu> menuList = menuJpaRepository.findMenuDetails(requestDTO.getDish());
       if(menuList.isPresent()){
           Menu menu = menuList.get();
           responseDTO.setDish_id(menu.getDishId());
           responseDTO.setCategory(menu.getCategory());
           responseDTO.setDish(menu.getDish());
       }
       else{
           return Optional.empty();
       }
        return menuList;
    }


    public ApiResponse fetchCompleteDishDetails(CompleteDetailsRequestDTO requestDTO){

            return null;
    }


}
