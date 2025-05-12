package com.example.foodpanda_microservices.service.impl;

import com.example.foodpanda_microservices.dto.request.UtrRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.foodpanda_microservices.dto.request.PaymentUtrRequest;
import com.example.foodpanda_microservices.repository.UtrJdbcRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MiddlewarePaymentService {

    @Autowired
    UtrJdbcRepository utrJdbcRepository;

    @Autowired
    ObjectMapper mapper;


    @Autowired
    UtrJdbcRepository repository;


    @Autowired
    private  KafkaTemplate<String, Object> jsonKafkaTemplate;
    private static final String TOPIC = "foodpanda-utr-service";

//    public MiddlewarePaymentService(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }

    
    public void pushMessageToKafkaConsumer(UtrRequest request) throws JsonProcessingException {
        log.info("Incoming Request,{}",request);
//        String SerializedString = mapper.writeValueAsString(request);
        jsonKafkaTemplate.send(TOPIC,"foodpanda_008", request.getUtrNumber());
        System.out.println("Message Sent : " + request);
    }



    @KafkaListener(topics = "foodpanda-utr-service",groupId = "utr")
    public void consumeMiddlewareResponse(String request) throws JsonMappingException, JsonProcessingException{
        try{
//            PaymentUtrRequest DeserializedRequest = objectMapper.readValue(request,PaymentUtrRequest.class);
            log.info("Deserialized PayoutUtmConsumerRequest: {}", request);
            PaymentUtrRequest request1 = PaymentUtrRequest.builder().utrNumber(request).build();
            repository.updateUtr(request1);
        }catch (Exception e){
            log.info("Exception at Consumer",e);
            jsonKafkaTemplate.send("foodpanda-utr-service.DLT",request);
        }
    }

    
//    public void udpateUtrNumber(PaymentUtrRequest request){
//        utrJdbcRepository.updateUtr(request);
//    }
    

}
