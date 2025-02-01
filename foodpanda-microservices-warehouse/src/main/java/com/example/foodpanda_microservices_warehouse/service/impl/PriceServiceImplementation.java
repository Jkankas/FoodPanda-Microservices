package com.example.foodpanda_microservices_warehouse.service.impl;

import com.example.foodpanda_microservices_warehouse.dto.request.AddPriceRequest;
import com.example.foodpanda_microservices_warehouse.dto.response.AddPriceResponse;
import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import com.example.foodpanda_microservices_warehouse.repository.PriceRepository;
import com.example.foodpanda_microservices_warehouse.service.PriceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Service
public class PriceServiceImplementation implements PriceService {


    @Autowired
    PriceRepository repository;



    public ApiResponse addPrice(AddPriceRequest request){
        repository.addPrice(request.getDish(),request.getPrice());
        return ApiResponse.prepareApiResponse("successfully added!");
    }



    public ApiResponse fetchPrice(){
        AddPriceResponse response = new AddPriceResponse();
        response.setPriceList(repository.fetchPrice());
        return ApiResponse.prepareApiResponse(response.getPriceList());
    }


    public ApiResponse fetchPriceByDish(String dish){
      Map<String,Object> priceInfo = repository.fetchPriceByDish(dish);
        return ApiResponse.prepareApiResponse(priceInfo);
    }


}
