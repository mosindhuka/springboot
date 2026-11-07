
package com.example.myfirstapp.controllers;

import com.example.myfirstapp.dtos.rag.SearchRequest;
import com.example.myfirstapp.dtos.rag.SearchResponse;
import com.example.myfirstapp.services.RetrievalService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final RetrievalService retrieval;

    public SearchController(RetrievalService retrieval) {
        this.retrieval = retrieval;
    }

    @PostMapping
    public SearchResponse search(
            @Valid @RequestBody SearchRequest request) {

        var results = retrieval.search(request.query());

        return new SearchResponse(
                results.stream()
                        .map(doc -> new SearchResponse.Result(
                                doc.getText(),
                                doc.getMetadata(),
                                doc.getId()))
                        .toList());
    }
}
