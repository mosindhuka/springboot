package com.example.myfirstapp.dtos.rag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChatRequest(
        @NotBlank
        @Size(max = 5000)
        String question
) {}
