package com.example.myfirstapp.controllers;

import com.example.myfirstapp.kafka.MessageProducer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageProducer producer;

    public MessageController(MessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/{message}")
    public String send(@PathVariable String message) {

        producer.sendToCluster1(message);

        return "Message sent";
    }
}
