package com.example.foodpanda_microservices_warehouse.controller.Rest;

import com.example.foodpanda_microservices_warehouse.dto.request.StockRequest;
import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import com.example.foodpanda_microservices_warehouse.service.StockService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse")
public class StockController {

        @Autowired
        StockService service;

        @Autowired
        private Logger log;

        @PostMapping("/addStock")
        public ResponseEntity<ApiResponse> addStock(  @Valid @RequestBody StockRequest request){
                log.info("request received at addStock Controller,{}",request);
                 return service.addStock(request);
        }


        @GetMapping("/fetchStock")
        public ResponseEntity<ApiResponse> fetchStock(){
                ApiResponse response = service.fetchStock();
                log.info("response received at fetchStock Controller,{}",response);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }


        @GetMapping("/fetchStockByDish/{dish}")
        public ApiResponse fetchStockByDish(@PathVariable String dish){
                log.info("request received at fetchStockByDish Controller,{}",dish);
                return service.fetchStockByDish(dish);
        }

        // Update Stock
        @PostMapping("/updateStock")
        public ApiResponse updateStock(@Param("stock")int stock, @Param("dish") String dish){
               return service.updateStock(stock,dish);
        }

}
