
package com.example.myfirstapp.services;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class DocumentParserService {

    public String extract(File file) throws Exception {
        String name = file.getName().toLowerCase();

        if (name.endsWith(".pdf")) {
            try (var document = Loader.loadPDF(file)) {
                return new PDFTextStripper().getText(document);
            }
        }

        if (name.endsWith(".docx")) {
            try (var in = new FileInputStream(file);
                 var doc = new XWPFDocument(in)) {

                StringBuilder text = new StringBuilder();

                doc.getParagraphs().forEach(p ->
                        text.append(p.getText()).append("\n"));

                doc.getTables().forEach(table -> {
                    table.getRows().forEach(row -> {
                        row.getTableCells().forEach(cell ->
                                text.append(cell.getText()).append(" | "));
                        text.append("\n");
                    });
                });

                return text.toString();
            }
        }

        if (name.endsWith(".txt")) {
            return java.nio.file.Files.readString(
                    file.toPath(), StandardCharsets.UTF_8);
        }

        if (name.endsWith(".xlsx") || name.endsWith(".xls")) {
            try (InputStream in = new FileInputStream(file);
                 Workbook workbook = WorkbookFactory.create(in)) {

                DataFormatter formatter = new DataFormatter();
                StringBuilder text = new StringBuilder();

                for (Sheet sheet : workbook) {
                    text.append("\nWorksheet: ")
                            .append(sheet.getSheetName()).append("\n");

                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            text.append(formatter.formatCellValue(cell))
                                    .append(" | ");
                        }
                        text.append("\n");
                    }
                }

                return text.toString();
            }
        }

        throw new IllegalArgumentException(
                "Unsupported document format: " + name);
    }
}