
package com.example.myfirstapp.services;

import com.example.myfirstapp.dtos.ChatRequest;
import com.example.myfirstapp.dtos.ChatResponse;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {

    private final ChatClient chatClient;

    private final ConcurrentHashMap<String, List<Message>>
            conversations = new ConcurrentHashMap<>();

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ChatResponse chat(ChatRequest request) {

        String conversationId =
                request.conversationId() == null
                        || request.conversationId().isBlank()
                        ? UUID.randomUUID().toString()
                        : request.conversationId();

        List<Message> history =
                conversations.computeIfAbsent(
                        conversationId,
                        id -> new ArrayList<>()
                );

        synchronized (history) {

            // Add current user message to context
            List<Message> messages =
                    new ArrayList<>(history);

            messages.add(
                    new UserMessage(request.message())
            );

            // Send conversation to Ollama
            String aiResponse = chatClient.prompt()
                    .messages(messages)
                    .call()
                    .content();

            if (aiResponse == null || aiResponse.isBlank()) {
                throw new IllegalStateException(
                        "AI returned an empty response"
                );
            }

            // Store conversation history
            history.add(
                    new UserMessage(request.message())
            );

            history.add(
                    new AssistantMessage(aiResponse)
            );

            return new ChatResponse(
                    conversationId,
                    aiResponse
            );
        }
    }

    public void clearConversation(String conversationId) {
        conversations.remove(conversationId);
    }
}
