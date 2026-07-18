package com.example.myfirstapp.kafka;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final KafkaTemplate<String, String> cluster1Template;
    private final KafkaTemplate<String, String> cluster2Template;


    public MessageProducer(
            @Qualifier("cluster1KafkaTemplate")
            KafkaTemplate<String, String> cluster1Template,

            @Qualifier("cluster2KafkaTemplate")
            KafkaTemplate<String, String> cluster2Template
    ) {
        this.cluster1Template = cluster1Template;
        this.cluster2Template = cluster2Template;
    }


    public void sendToCluster1(String message) {
        cluster1Template.send("test-topic", message);
    }


    public void sendToCluster2(String message) {
        cluster2Template.send("test-topic", message);
    }
}
