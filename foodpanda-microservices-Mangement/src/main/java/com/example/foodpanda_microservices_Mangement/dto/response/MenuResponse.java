package com.example.foodpanda_microservices_Mangement.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuResponse {

    private Object result;
    private String message;
    private Integer status;

}
