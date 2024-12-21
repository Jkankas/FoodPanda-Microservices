package com.example.foodpanda_microservices_Mangement.service;

import com.example.foodpanda_microservices_Mangement.dto.request.PriceRequest;

public interface PriceService {

    public int addPrice(PriceRequest request);

}
