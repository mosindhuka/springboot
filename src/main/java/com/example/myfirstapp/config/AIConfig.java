
package com.example.myfirstapp.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AIConfig {

//    @Bean
//    public ChatClient chatClient(
//            ChatClient.Builder builder) {
//
//        return builder
//                .defaultSystem("""
//                    You are a helpful AI assistant.
//                    Answer questions clearly and accurately.
//                    If you don't know something, say so.
//                    """)
//                .build();
//    }

    @Bean
    ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
}
