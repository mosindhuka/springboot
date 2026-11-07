package com.example.myfirstapp.dtos.rag;

import java.util.List;

public record SearchResponse(List<Result> results) {
    public record Result(
            String content,
            Object metadata,
            String id
    ) {}
}
