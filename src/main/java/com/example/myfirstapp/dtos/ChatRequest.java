
package com.example.myfirstapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChatRequest(

        String conversationId,

        @NotBlank(message = "Message cannot be empty")
        @Size(max = 10000, message = "Message is too long")
        String message

) {}
