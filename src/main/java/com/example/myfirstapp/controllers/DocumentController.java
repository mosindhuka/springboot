
package com.example.myfirstapp.controllers;

import com.example.myfirstapp.dtos.rag.DocumentResponse;
import com.example.myfirstapp.models.pg.StoredDocument;
import com.example.myfirstapp.repositories.pg.DocumentRepository;
import com.example.myfirstapp.services.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService service;
    private final DocumentRepository repository;

    public DocumentController(
            DocumentService service,
            DocumentRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DocumentResponse upload(
            @RequestPart("file") MultipartFile file) throws Exception {
        return response(service.upload(file));
    }

    @GetMapping
    public List<DocumentResponse> list() {
        return repository.findAll().stream()
                .map(this::response)
                .toList();
    }

    @GetMapping("/{id}")
    public DocumentResponse get(@PathVariable UUID id) {
        return repository.findById(id)
                .map(this::response)
                .orElseThrow(() ->
                        new IllegalArgumentException("Document not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        // Use the document lifecycle service to delete vectors,
        // metadata and the original file together.
        serviceDelete(id);
    }

    private void serviceDelete(UUID id) {
        throw new UnsupportedOperationException(
                "Implement coordinated deletion before enabling this endpoint");
    }

    private DocumentResponse response(StoredDocument d) {
        return new DocumentResponse(
                d.getId(),
                d.getOriginalFilename(),
                d.getStatus(),
                d.getFileSize(),
                d.getChunkCount(),
                d.getErrorMessage(),
                d.getCreatedAt());
    }
}
