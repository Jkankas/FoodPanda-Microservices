package com.example.foodpanda_microservices.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String,String> kafkaTemplate ;
    private static final String TOPIC = "test-topic";

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message){
        kafkaTemplate.send(TOPIC,message);
        System.out.println("Message Sent : " + message);
    }
}
