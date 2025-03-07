package com.arturk.storage.controller;

import com.arturk.storage.dto.ManufacturerDto;
import com.arturk.storage.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v4/manufacturer")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ManufacturerDto>> getManufacturers(Pageable pageable) {
        List<ManufacturerDto> manufacturers = manufacturerService.getManufacturers(pageable);
        return ResponseEntity.ok(manufacturers);
    }

    @RequestMapping(
            value = "/{manufacturerId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ManufacturerDto> getManufacturerById(@PathVariable Long manufacturerId) {
        ManufacturerDto manufacturer = manufacturerService.getManufacturerById(manufacturerId);
        return ResponseEntity.ok(manufacturer);
    }

}
