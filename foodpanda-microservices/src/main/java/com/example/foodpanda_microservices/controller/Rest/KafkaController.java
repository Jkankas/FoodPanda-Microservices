package com.example.foodpanda_microservices.controller.Rest;

import com.example.foodpanda_microservices.service.impl.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {


    @Autowired
   private KafkaProducer kafkaProducer;


    @GetMapping("/send-message/{message}")
    public String sendMessage(@PathVariable String message){
        kafkaProducer.sendMessage(message);
        return "Message Sent: "+message;
    }

}
