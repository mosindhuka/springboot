package com.example.myfirstapp.controllers;

import com.example.myfirstapp.agent.UserAgent;
import com.example.myfirstapp.dtos.ChatRequest;
import com.example.myfirstapp.dtos.ChatResponse;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final UserAgent userAgent;

    public AgentController(UserAgent userAgent) {
        this.userAgent = userAgent;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(
            @Valid @RequestBody ChatRequest request) {

        String response =
                userAgent.chat(request.message());
        System.out.println(response);
        return ResponseEntity.ok(
                new ChatResponse("1", response)
        );
    }
}