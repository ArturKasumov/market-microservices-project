package com.arturk.storage.controller;

import com.arturk.storage.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v4/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @RequestMapping(
            value = "/manufacturer",
            method = RequestMethod.GET
    )
    public ResponseEntity<Void> generateManufacturerPdfReport() {
        reportService.generateManufacturerPdfReport();
        return ResponseEntity.ok().build();
    }
}
