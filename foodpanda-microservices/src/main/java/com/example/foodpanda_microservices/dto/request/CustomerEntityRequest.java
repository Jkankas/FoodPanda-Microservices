package com.example.foodpanda_microservices.dto.request;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerEntityRequest {


    private String fullName;
    private String address;
    private String landmark;
    private Integer pin;
}
