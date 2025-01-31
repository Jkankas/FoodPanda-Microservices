package com.example.foodpanda_microservices.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerValidateOtpRequest {
    @NotBlank(message = "phoneNumber is mandatory")
    private String phoneNumber;
    @NotBlank(message = "OTP cannot be Empty/Null")
    private String otp;
}
