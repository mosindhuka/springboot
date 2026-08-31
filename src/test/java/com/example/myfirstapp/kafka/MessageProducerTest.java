package com.example.myfirstapp.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageProducerTest {

    @Mock
    private KafkaTemplate<String, String> cluster1Template;

    @Mock
    private KafkaTemplate<String, String> cluster2Template;

    private MessageProducer messageProducer;

    @BeforeEach
    void setUp() {
        messageProducer = new MessageProducer(
                cluster1Template,
                cluster2Template
        );
    }

    @Test
    void shouldSendMessageToCluster1() {
        String message = "hello cluster 1";

        messageProducer.sendToCluster1(message);

        verify(cluster1Template).send("test-topic", message);
    }

    @Test
    void shouldSendMessageToCluster2() {
        String message = "hello cluster 2";

        messageProducer.sendToCluster2(message);

        verify(cluster2Template).send("test-topic", message);
    }
}