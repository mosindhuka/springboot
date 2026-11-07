package com.example.myfirstapp.dtos.rag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SearchRequest(
        @NotBlank
        @Size(max = 5000)
        String query
) {}
