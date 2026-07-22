//package com.example.myfirstapp.kafka;
//
//import org.springframework.kafka.annotation.BackOff;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.RetryableTopic;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageConsumer {
//
//    @RetryableTopic(
//            attempts = "2",
//            backOff = @BackOff(delay = 1000)
//    )
//    @KafkaListener(
//            topics = "test-topic",
//            containerFactory = "cluster1KafkaListenerContainerFactory"
//    )
//    public void consumeOrder(String message) {
//        System.out.println("Message received : " + message);
//    }
//}
