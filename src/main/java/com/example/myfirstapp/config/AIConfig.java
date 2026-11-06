
package com.example.myfirstapp.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    public ChatClient chatClient(
            ChatClient.Builder builder) {

        return builder
                .defaultSystem("""
                    You are a helpful AI assistant.
                    Answer questions clearly and accurately.
                    If you don't know something, say so.
                    """)
                .build();
    }
}
