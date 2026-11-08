
package com.example.myfirstapp.services;

import com.example.myfirstapp.config.RagProperties;
import com.example.myfirstapp.models.pg.StoredDocument;
import com.example.myfirstapp.repositories.pg.DocumentRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.*;

@Service
public class IngestionService {

    private final DocumentRepository repository;
    private final DocumentParserService parser;
    private final ChunkingService chunker;
    private final FileStorageService storage;
    private final VectorStore vectorStore;
    private final RagProperties properties;

    public IngestionService(
            DocumentRepository repository,
            DocumentParserService parser,
            ChunkingService chunker,
            FileStorageService storage,
            VectorStore vectorStore,
            RagProperties properties) {
        this.repository = repository;
        this.parser = parser;
        this.chunker = chunker;
        this.storage = storage;
        this.vectorStore = vectorStore;
        this.properties = properties;
    }

    @Async("ingestionExecutor")
    public void ingest(UUID documentId) {
        System.out.println("1. Starting ingestion: " + documentId);

        StoredDocument doc = repository.findById(documentId)
                .orElseThrow();

        System.out.println("2. Document retrieved successfully");


        try {
            Path file = storage.resolve(doc.getStoredFilename());
            String content = parser.extract(file.toFile());

            System.out.println("3. PDF extracted successfully");


            List<String> chunks = chunker.split(
                    content,
                    properties.chunkSize(),
                    properties.chunkOverlap());

            System.out.println("4. Chunks generated: " + chunks.size());


            if (chunks.isEmpty()) {
                throw new IllegalStateException(
                        "Document contains no extractable text");
            }

            List<Document> documents = new ArrayList<>();

            for (int i = 0; i < chunks.size(); i++) {
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("documentId", documentId.toString());
                metadata.put("filename", doc.getOriginalFilename());
                metadata.put("chunkIndex", i);

                documents.add(new Document(chunks.get(i), metadata));
            }

            System.out.println("5. Saving vectors...");


            vectorStore.add(documents);

            System.out.println("6. Vectors saved successfully");


            updateStatus(documentId, "COMPLETED", null, chunks.size());

        } catch (Exception e) {
            e.printStackTrace();

            updateStatus(
                    documentId,
                    "FAILED",
                    safeError(e),
                    0);
        }
    }

    @Transactional
    public void updateStatus(
            UUID id, String status, String error, int chunks) {

        StoredDocument doc = repository.findById(id).orElseThrow();
        doc.setStatus(status);
        doc.setErrorMessage(error);
        doc.setChunkCount(chunks);
        repository.save(doc);
    }

    private String safeError(Exception e) {
        String message = e.getMessage();
        if (message == null) message = e.getClass().getSimpleName();
        return message.substring(0, Math.min(message.length(), 1000));
    }
}
