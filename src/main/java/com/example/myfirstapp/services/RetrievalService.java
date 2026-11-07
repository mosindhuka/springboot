
package com.example.myfirstapp.services;

import com.example.myfirstapp.config.RagProperties;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RetrievalService {

    private final VectorStore vectorStore;
    private final RagProperties properties;

    public RetrievalService(
            VectorStore vectorStore,
            RagProperties properties) {
        this.vectorStore = vectorStore;
        this.properties = properties;
    }

    public List<Document> search(String query) {
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(properties.topK())
                .similarityThreshold(properties.similarityThreshold())
                .build();

        return vectorStore.similaritySearch(request);
    }

    public List<Document> searchByDocument(
            String query, UUID documentId) {

        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(properties.topK())
                .similarityThreshold(properties.similarityThreshold())
                .filterExpression(
                        "documentId == '" + documentId + "'")
                .build();

        return vectorStore.similaritySearch(request);
    }
}