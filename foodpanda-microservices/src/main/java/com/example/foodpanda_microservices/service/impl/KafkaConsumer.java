package com.example.foodpanda_microservices.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private String latestMessage;


    @KafkaListener(topics = "test-topic" ,groupId = "my-group",autoStartup = "false")
    public void consume(ConsumerRecord<String,String> record){
        System.out.println("Received Message: " + record.value());
        latestMessage =  record.value();
    }



    public String returnMessage(){
        return latestMessage;
    }


//    @KafkaListener(topics = "test-topic" ,groupId = "my-group")
//    public String consume(String message){
//        System.out.println("Received Message: " + message);
//        return message;
//    }


}
