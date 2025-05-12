package com.example.foodpanda_microservices.controller.Rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.foodpanda_microservices.dto.request.UtrRequest;
import com.example.foodpanda_microservices.dto.response.ApiResponse;
import com.example.foodpanda_microservices.service.impl.MiddlewarePaymentService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class PaymentController {


    // Expose a Middleware API to receive payment status from the payment gateway


    @Autowired
    MiddlewarePaymentService paymentService;


    @PostMapping("/middlware/payment/service")
    public ApiResponse pushToKafkaTopic(@RequestBody UtrRequest request) throws JsonProcessingException {
        log.info("Intecepted Message,{}",request);
        paymentService.pushMessageToKafkaConsumer(request);
        return ApiResponse.prepareApiResponse("Successfully Pushed to Kafka Topic");
    }
}
