//package com.example.myfirstapp.kafka;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageConsumer {
//
//    @KafkaListener(
//            topics = "test-topic",
//            containerFactory = "cluster1KafkaListenerContainerFactory"
//    )
//    public void consumeOrder(String message) {
//        System.out.println("Message received : " + message);
//    }
//}
