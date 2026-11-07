
package com.example.myfirstapp.controllers;

import com.example.myfirstapp.dtos.rag.ChatRequest;
import com.example.myfirstapp.dtos.rag.ChatResponse;
import com.example.myfirstapp.services.RagService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rag/chat")
public class RagChatController {

    private final RagService ragService;

    public RagChatController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping
    public ChatResponse chat(
            @Valid @RequestBody ChatRequest request) {
        return ragService.chat(request);
    }
}
