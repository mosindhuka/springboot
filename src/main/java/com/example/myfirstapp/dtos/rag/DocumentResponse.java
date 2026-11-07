package com.example.myfirstapp.dtos.rag;

import java.time.Instant;
import java.util.UUID;

public record DocumentResponse(
        UUID id,
        String filename,
        String status,
        long size,
        int chunks,
        String error,
        Instant createdAt
) {}
