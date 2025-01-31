package com.example.foodpanda_microservices.dto.request;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerEntityRequest {

    private long id;
    private String customerId;
    private String fullName;
    private String address;
    private String landmark;
    private String pin;
    private String city;
    private String state;

}
