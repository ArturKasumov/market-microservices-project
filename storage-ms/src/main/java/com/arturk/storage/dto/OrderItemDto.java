package com.arturk.storage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    private Long productId;
    private Integer quantity;
}
