package com.arturk.storage.dto;

import com.arturk.storage.enums.ProductCategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long id;

    private Long manufacturerId;

    private String name;

    private Integer quantity;

    private ProductCategoryEnum category;
}
