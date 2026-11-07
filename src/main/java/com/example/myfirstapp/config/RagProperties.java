
package com.example.myfirstapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rag")
public record RagProperties(
        String storagePath,
        int chunkSize,
        int chunkOverlap,
        int topK,
        double similarityThreshold,
        long maxUploadSize
) {}