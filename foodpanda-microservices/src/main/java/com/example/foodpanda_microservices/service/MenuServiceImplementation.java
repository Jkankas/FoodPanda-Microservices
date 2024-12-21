package com.example.foodpanda_microservices.service;

import com.example.foodpanda_microservices.dto.request.MenuRequest;
import com.example.foodpanda_microservices.dto.request.UserEntityRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.FetchAllMenuResponse;
import com.example.foodpanda_microservices.dto.response.MenuResponse;
import com.example.foodpanda_microservices.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MenuServiceImplementation implements MenuService{

     @Autowired
    MenuRepository repository;


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
            response.setId((Integer) map.get("dish_id"));
            response.setDish((String)map.get("dish"));
            response.setCategory((String)map.get("category"));
            menuResponse.getMenuList().add(response);
        }
        return menuResponse;
    }


    public ApiResponse createUser(UserEntityRequest request){
        repository.createProfile(request.getUsername(),request.getPassword(),request.getDepartment(),
                request.getEmail());
        return ApiResponse.prepareApiResponse("Successfully Added");
    }


}
