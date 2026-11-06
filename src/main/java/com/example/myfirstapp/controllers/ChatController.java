
package com.example.myfirstapp.controllers;

import com.example.myfirstapp.dtos.ChatRequest;
import com.example.myfirstapp.dtos.ChatResponse;
import com.example.myfirstapp.services.ChatService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(
            @Valid @RequestBody ChatRequest request) {

        ChatResponse response =
                chatService.chat(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{conversationId}")
    public ResponseEntity<Map<String, String>> clearChat(
            @PathVariable String conversationId) {

        chatService.clearConversation(conversationId);

        return ResponseEntity.ok(
                Map.of("message", "Conversation cleared")
        );
    }
}
