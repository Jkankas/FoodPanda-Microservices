package com.example.foodpanda_microservices.controller.Rest;

import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.service.impl.AuditServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditServiceImpl auditService;


    // Fetch list of orders which is delivered with Customer Id

    @GetMapping("/delivered-items")
    public ApiResponse deliveredList(){
        return auditService.fetchDeliveredItems();
    }


    // Calculate Total Order sum of individual Customers.
    @GetMapping("/total-orders")
    public ApiResponse fetchOrdersTotal(){
        return auditService.ordersTotal();
    }


    // Extract Duplicate category from Menu List

    @GetMapping("/distinct-category")
    public ApiResponse fetchDistinctCategory(){
        return auditService.ordersTotal();
    }

}
