
package com.example.myfirstapp.models;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "stored_documents")
public class StoredDocument {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private String storedFilename;

    private String contentType;

    private long fileSize;

    @Column(nullable = false)
    private String checksum;

    @Column(nullable = false)
    private String status;

    @Column(columnDefinition = "text")
    private String errorMessage;

    private int chunkCount;

    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getOriginalFilename() { return originalFilename; }
    public void setOriginalFilename(String v) { originalFilename = v; }

    public String getStoredFilename() { return storedFilename; }
    public void setStoredFilename(String v) { storedFilename = v; }

    public String getContentType() { return contentType; }
    public void setContentType(String v) { contentType = v; }

    public long getFileSize() { return fileSize; }
    public void setFileSize(long v) { fileSize = v; }

    public String getChecksum() { return checksum; }
    public void setChecksum(String v) { checksum = v; }

    public String getStatus() { return status; }
    public void setStatus(String v) { status = v; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String v) { errorMessage = v; }

    public int getChunkCount() { return chunkCount; }
    public void setChunkCount(int v) { chunkCount = v; }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}