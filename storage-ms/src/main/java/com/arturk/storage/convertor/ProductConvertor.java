package com.arturk.storage.convertor;

import com.arturk.storage.dto.ProductDto;
import com.arturk.storage.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductConvertor {

    @Mapping(target = "manufacturerId", source = "manufacturer.id")
    ProductDto toProductDto(ProductEntity source);
}

