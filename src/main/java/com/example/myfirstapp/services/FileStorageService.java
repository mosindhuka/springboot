
package com.example.myfirstapp.services;

import com.example.myfirstapp.config.RagProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path root;

    public FileStorageService(RagProperties properties)
            throws IOException {
        root = Paths.get(properties.storagePath())
                .toAbsolutePath().normalize();
        Files.createDirectories(root);
    }

    public Path save(UUID id, MultipartFile file) throws IOException {
        String extension = extension(file.getOriginalFilename());
        Path target = root.resolve(id + extension).normalize();

        if (!target.startsWith(root)) {
            throw new SecurityException("Invalid storage path");
        }

        try (var input = file.getInputStream()) {
            Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
        }

        return target;
    }

    public Path resolve(String filename) {
        Path target = root.resolve(filename).normalize();
        if (!target.startsWith(root)) {
            throw new SecurityException("Invalid storage path");
        }
        return target;
    }

    public void delete(String filename) throws IOException {
        Files.deleteIfExists(resolve(filename));
    }

    private String extension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("Missing file extension");
        }

        String ext = filename.substring(filename.lastIndexOf('.') + 1)
                .toLowerCase();

        if (!java.util.Set.of("pdf", "docx", "txt", "xlsx", "xls")
                .contains(ext)) {
            throw new IllegalArgumentException("Unsupported file type");
        }

        return "." + ext;
    }
}
