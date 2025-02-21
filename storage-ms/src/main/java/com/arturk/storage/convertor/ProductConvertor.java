package com.arturk.storage.convertor;

import com.arturk.storage.dto.ProductDto;
import com.arturk.storage.entity.ProductEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductConvertor {

    @Mappings(value = {
            @Mapping(target = "manufacturer", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    ProductEntity toProductEntity(ProductDto dto);

    @Mapping(target = "manufacturerId", source = "manufacturer.id")
    ProductDto toProductDto(ProductEntity source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    @Mappings(value = {
            @Mapping(target = "manufacturer", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    void patchProductEntity(ProductDto dto, @MappingTarget ProductEntity entity);

    @Mappings(value = {
            @Mapping(target = "manufacturer", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    void updateProductEntity(ProductDto dto, @MappingTarget ProductEntity entity);
}

