package com.example.foodpanda_microservices.service;

import com.example.foodpanda_microservices.dto.request.MenuRequest;
import com.example.foodpanda_microservices.dto.request.UserEntityRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.FetchAllMenuResponse;

public interface MenuService {

    public int addMenu(MenuRequest request);
    public FetchAllMenuResponse fetchAllMenuList();
    public ApiResponse createUser(UserEntityRequest request);

}
