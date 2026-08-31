//package com.example.myfirstapp.kafka;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class MessageConsumerTest {
//
//    private final MessageConsumer messageConsumer = new MessageConsumer();
//
//    private final ByteArrayOutputStream outputStream =
//            new ByteArrayOutputStream();
//
//    private PrintStream originalOut;
//
//    @BeforeEach
//    void setUp() {
//        originalOut = System.out;
//        System.setOut(new PrintStream(outputStream));
//    }
//
//    @AfterEach
//    void tearDown() {
//        System.setOut(originalOut);
//    }
//
//    @Test
//    void shouldPrintReceivedMessage() {
//        String message = "hello kafka";
//
//        messageConsumer.consumeOrder(message);
//
//        assertEquals(
//                "Message received : hello kafka",
//                outputStream.toString().trim()
//        );
//    }
//}