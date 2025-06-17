package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.dto.response.OrdersTotal;
import com.example.foodpanda_microservices.enums.OrderStatus;
import com.example.foodpanda_microservices.repository.impl.AuditRepositoryImpl;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuditServiceImpl {


    @Autowired
    private AuditRepositoryImpl auditRepository;



    public ApiResponse fetchDeliveredItems(){
        List<Map<String,Object>> ordersList = auditRepository.orderedItems();
        List<Long> orderIds = new ArrayList<>();

        // Basic Stream API implementation
        List<Map<String,Object>> result =  ordersList.stream().filter(map1-> {
           return map1.containsKey("order_status") && OrderStatus.DELIVERED.name().equals(map1.get("order_status"));
        }).collect(Collectors.toList());

        // Implemented map function of Streams API
        orderIds =  ordersList.stream().filter(map1-> {
            return map1.containsKey("order_status") && OrderStatus.DELIVERED.name().equals(map1.get("order_status"));
        }).map(map1-> (Long)map1.get("order_id")).collect(Collectors.toList());

        return ApiResponse.prepareApiResponse(orderIds);
  }



    public ApiResponse ordersTotal(){
        List<Map<String,Object>> ordersTotal = auditRepository.ordersTotal();

        List<OrdersTotal> ordersTotalList = ordersTotal.stream().filter(orders->
                orders.get("order_status").equals("DELIVERED")).map(orders->
                {
                    OrdersTotal total = new OrdersTotal();
                    total.setId((long) orders.get("order_id"));
                    total.setOrderStatus((String) orders.get("order_status"));
                    total.setOrderedAt((LocalDateTime) orders.get("ordered_at"));
                    total.setPrice((Double) orders.get("price"));
                    total.setCustomerId((String) orders.get("customer_id"));
                    return total;
                }
        ).collect(Collectors.toList());

        return ApiResponse.prepareApiResponse(ordersTotalList);
    }



//    public ApiResponse distinctCategories(){
//        List<Map<String,Object>> categories = auditRepository.distinctCategories();
//        Set<String> uniqueCategories = categories.stream().flatMap()
//    }




















    }