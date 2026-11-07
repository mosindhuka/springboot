package com.example.myfirstapp.dtos.rag;

import java.util.List;

public record ChatResponse(
        String answer,
        List<Source> sources
) {
    public record Source(
            String filename,
            Object chunkIndex,
            String chunkId
    ) {}
}
