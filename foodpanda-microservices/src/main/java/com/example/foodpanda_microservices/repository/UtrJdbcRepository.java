package com.example.foodpanda_microservices.repository;

import com.example.foodpanda_microservices.dto.request.PaymentUtrRequest;

public interface UtrJdbcRepository {

    String UPDATE_UTR = "update transaction_history set utr = ? where order_id = ?";

    
    void updateUtr(PaymentUtrRequest request);

}
