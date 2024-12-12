package com.arturk.storage.convertor;

import com.arturk.storage.dto.ManufacturerDto;
import com.arturk.storage.entity.ManufacturerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManufacturerConvertor {

    ManufacturerDto toManufacturerDto(ManufacturerEntity source);
}

