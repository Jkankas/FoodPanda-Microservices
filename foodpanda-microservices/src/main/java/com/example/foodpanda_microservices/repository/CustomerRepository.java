package com.example.foodpanda_microservices.repository;

import java.time.LocalDateTime;
import java.util.Map;

public interface CustomerRepository {

    String SEARCH_CUSTOMER_ID = "select customer_id from customer_profile where phone_number = ?";


     Map<String,Object> searchCustomId(String phone);

}
