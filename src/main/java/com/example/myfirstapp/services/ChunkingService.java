
package com.example.myfirstapp.services;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkingService {

    public List<String> split(
            String text, int chunkSize, int overlap) {

        if (text == null || text.isBlank()) {
            return List.of();
        }

        if (chunkSize <= 0 || overlap < 0 || overlap >= chunkSize) {
            throw new IllegalArgumentException("Invalid chunk settings");
        }

        List<String> chunks = new ArrayList<>();
        int start = 0;

        while (start < text.length()) {
            int end = Math.min(start + chunkSize, text.length());

            if (end < text.length()) {
                int boundary = text.lastIndexOf(' ', end);
                if (boundary > start + chunkSize / 2) {
                    end = boundary;
                }
            }

            String chunk = text.substring(start, end).trim();

            if (!chunk.isEmpty()) {
                chunks.add(chunk);
            }

            if (end == text.length()) {
                break;
            }

            start = end - overlap;
        }

        return chunks;
    }
}
