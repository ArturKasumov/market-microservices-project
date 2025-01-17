package com.arturk.storage.service;

import com.arturk.storage.entity.repository.ManufacturerRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
@Slf4j
public class ReportService {

    @Value("${reports.location}")
    private String reportLocation;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private S3Client s3Client;

    public void generateManufacturerPDFReport() {
        Document document = new Document();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);

        String fileName = "manufacturer-" + formattedDate + ".pdf";
        Path filePath = Paths.get(reportLocation + fileName);
        try {
            Files.createDirectories(filePath.getParent());
            PdfWriter.getInstance(document, Files.newOutputStream(filePath));
            document.open();

            PdfPTable table = new PdfPTable(2);
            addTableHeader(table);
            addRows(table);
            document.add(table);
            document.close();
        } catch (Exception e) {
            log.error("Error occurred during generating manufacturer report", e);
            throw new RuntimeException(e.getMessage());
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("storage-ms-reports")
                .key(fileName)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(filePath));
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("MANUFACTURER_ID", "MANUFACTURER_NAME")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table) {
        manufacturerRepository.findAll()
                .forEach(record -> {
                    table.addCell(String.valueOf(record.getId()));
                    table.addCell(record.getName());
                });
    }
}
