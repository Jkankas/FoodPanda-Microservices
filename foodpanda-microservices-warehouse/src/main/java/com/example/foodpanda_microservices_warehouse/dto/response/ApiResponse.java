package com.example.foodpanda_microservices_warehouse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;



    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class ApiResponse {


        private Integer Status;
        private String message;
        private Object result;
        @Builder.Default
        private long timestamp = Instant.now().toEpochMilli();


        public static ApiResponse prepareApiResponse (Object result){
            ApiResponse response = new ApiResponse();
            response.setResult(result);
            response.setMessage("Success");
            response.setStatus(200);
            return response;
        }



        public static ApiResponse prepareFailApiResponse (Object result){
            ApiResponse response = new ApiResponse();
            response.setResult(result);
            response.setMessage("Failure");
            response.setStatus(400);
            return response;
        }



        public static ApiResponse customExceptionResponse(String message, Integer status){
            ApiResponse response = new ApiResponse();
            response.setMessage(message);
            response.setStatus(status);
            return response;
        }



    }
