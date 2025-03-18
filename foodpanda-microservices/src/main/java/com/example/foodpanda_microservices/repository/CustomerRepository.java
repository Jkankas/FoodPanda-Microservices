package com.example.foodpanda_microservices.repository;

import java.time.LocalDateTime;
import java.util.Map;

public interface CustomerRepository {

    String SEARCH_CUSTOMER_ID = "select customer_id from customer_profile where phone_number = ?";
    String SEARCH_CUSTOMER_BY_ID = "select * from customer_profile where customer_id = ?";


     Map<String,Object> searchCustomId(String phone);
     Map<String,Object> searchCustomerDetails(String id);

}
