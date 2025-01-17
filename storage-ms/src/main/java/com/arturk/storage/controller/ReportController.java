package com.arturk.storage.controller;

import com.arturk.storage.service.ReportService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v4/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/manufacturer", method = RequestMethod.GET)
    public ResponseEntity<Void> generateManufacturerPDFReport() throws DocumentException, IOException {
        reportService.generateManufacturerPDFReport();
        return ResponseEntity.ok().build();
    }
}
