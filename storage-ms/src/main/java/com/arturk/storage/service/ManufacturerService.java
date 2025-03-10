package com.arturk.storage.service;

import com.arturk.storage.convertor.ManufacturerConvertor;
import com.arturk.storage.dto.ManufacturerDto;
import com.arturk.storage.entity.repository.ManufacturerRepository;
import com.arturk.storage.exception.ManufacturerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerConvertor manufacturerConvertor;

    public List<ManufacturerDto> getManufacturers() {
        return manufacturerRepository.findAll()
                .stream()
                .map(manufacturerConvertor::toManufacturerDto)
                .collect(Collectors.toList());
    }

    public List<ManufacturerDto> getManufacturers(Pageable pageable) {
        return manufacturerRepository.findAll(pageable)
                .stream()
                .map(manufacturerConvertor::toManufacturerDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "manufacturer", key = "#manufacturerId")
    public ManufacturerDto getManufacturerById(Long manufacturerId) {
        return manufacturerRepository.findById(manufacturerId)
                .map(manufacturerConvertor::toManufacturerDto)
                .orElseThrow(ManufacturerNotFoundException::new);
    }
}
