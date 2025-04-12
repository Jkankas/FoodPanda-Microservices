package com.example.foodpanda_microservices_warehouse.controller.Rest;

import com.example.foodpanda_microservices_warehouse.dto.request.StockRequest;
import com.example.foodpanda_microservices_warehouse.dto.request.StockRequestV1;
import com.example.foodpanda_microservices_warehouse.dto.request.UpdateStockRequestV1;
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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


        @PostMapping("/fetchStockByDishV1")
        public ApiResponse fetchStockByDishV1(@RequestBody StockRequestV1 requestV1) {
                log.info("request received at fetchStockByDish Controller,{}",requestV1.getDishes());
                return service.fetchStockByDishV1(requestV1.getDishes());
        }


        // Update Stock
        @PostMapping("/updateStock")
        public ApiResponse updateStock(@Param("stock")int stock, @Param("dish") String dish){
               return service.updateStock(stock, dish);
        }

        // Update Stock-V1
        @PostMapping("/updateStockV1")
        public ApiResponse updateStockV1(@RequestBody UpdateStockRequestV1 requestV1){
                return service.updateStockV1(requestV1);
        }
}
