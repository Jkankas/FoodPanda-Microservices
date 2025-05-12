package com.example.foodpanda_microservices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {



    @Autowired
    KafkaTemplate<String,Object> jsonKafkaTemplate;

//    private final KafkaTemplate<String,String> kafkaTemplate ;
    private static final String TOPIC = "test-topic";

//    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
//        this.jsonKafkaTemplate = kafkaTemplate;
//    }

    public void sendMessage(String message){
        jsonKafkaTemplate.send(TOPIC,message);
        System.out.println("Message Sent : " + message);
    }
}
