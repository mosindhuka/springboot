
package com.example.myfirstapp.services;

import com.example.myfirstapp.config.RagProperties;
import com.example.myfirstapp.models.pg.StoredDocument;
import com.example.myfirstapp.repositories.pg.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository repository;
    private final FileStorageService storage;
    private final IngestionService ingestion;
    private final RagProperties properties;

    public DocumentService(
            DocumentRepository repository,
            FileStorageService storage,
            IngestionService ingestion,
            RagProperties properties) {
        this.repository = repository;
        this.storage = storage;
        this.ingestion = ingestion;
        this.properties = properties;
    }

    public StoredDocument upload(MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }

        if (file.getSize() > properties.maxUploadSize()) {
            throw new IllegalArgumentException("File exceeds size limit");
        }

        String original = file.getOriginalFilename();
        if (original == null || original.isBlank()) {
            throw new IllegalArgumentException("Filename is required");
        }

        String checksum = checksum(file);

        if (repository.findByChecksum(checksum).isPresent()) {
            throw new IllegalArgumentException(
                    "Duplicate document already uploaded");
        }

        UUID id = UUID.randomUUID();
        Path path = storage.save(id, file);

        StoredDocument doc = new StoredDocument();
        doc.setId(id);
        doc.setOriginalFilename(original);
        doc.setStoredFilename(path.getFileName().toString());
        doc.setContentType(file.getContentType());
        doc.setFileSize(file.getSize());
        doc.setChecksum(checksum);
        doc.setStatus("PROCESSING");

        try {
            repository.saveAndFlush(doc);
        } catch (Exception e) {
            storage.delete(path.getFileName().toString());
            throw e;
        }

        ingestion.ingest(id);
        return doc;
    }

    private String checksum(MultipartFile file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        try (InputStream in = file.getInputStream()) {
            byte[] buffer = new byte[8192];
            int count;

            while ((count = in.read(buffer)) != -1) {
                digest.update(buffer, 0, count);
            }
        }

        return HexFormat.of().formatHex(digest.digest());
    }
}