package com.example.foodpanda_microservices_warehouse.controller.Rest;

import com.example.foodpanda_microservices_warehouse.dto.request.StockRequest;
import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import com.example.foodpanda_microservices_warehouse.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse")
public class StockController {

        @Autowired
        StockService service;


        @PostMapping("/addStock")
        public ResponseEntity<ApiResponse> addStock(@RequestBody StockRequest request){
           return service.addStock(request);
        }


        @GetMapping("/fetchStock")
        public ResponseEntity<ApiResponse> fetchStock(){
                ApiResponse response = service.fetchStock();
                return new ResponseEntity<>(response, HttpStatus.OK);
        }


}
