
package com.example.myfirstapp.repositories;

import com.example.myfirstapp.models.StoredDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;

public interface DocumentRepository
        extends JpaRepository<StoredDocument, UUID> {

    Optional<StoredDocument> findByChecksum(String checksum);
}