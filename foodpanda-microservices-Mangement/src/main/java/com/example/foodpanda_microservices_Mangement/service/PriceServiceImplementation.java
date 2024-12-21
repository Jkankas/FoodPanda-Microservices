package com.example.foodpanda_microservices_Mangement.service;
import com.example.foodpanda_microservices_Mangement.configuration.ApplicationProperties;
import com.example.foodpanda_microservices_Mangement.dto.request.PriceRequest;
import com.example.foodpanda_microservices_Mangement.dto.response.MenuResponse;
import com.example.foodpanda_microservices_Mangement.repository.PriceRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class PriceServiceImplementation implements PriceService {


    @Autowired
    PriceRepositoryImplementation repo;

    @Autowired
    ApplicationProperties properties;

    @Autowired
    RestTemplate template;


    public int addPrice(PriceRequest request){

        ResponseEntity<MenuResponse> response = template.exchange(properties.getMenu_list(), HttpMethod.GET,null
        ,MenuResponse.class);
       Object obj = response.getBody().getResult();
        System.out.println(obj);

        Map<String,Object> extractMenu = (Map<String,Object>) obj;

        List<Map<String,Object>> list = (List<Map<String,Object>>) extractMenu.get("menuList");

        for(Map<String,Object> map : list){
            if(map.get("dish").equals(request.getDish())){
                return repo.addPrice(request.getPrice(),request.getDish());
            }
        }

        return -1;

    }


}
