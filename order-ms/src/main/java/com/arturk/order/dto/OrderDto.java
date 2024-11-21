package com.arturk.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDto {

    private List<OrderItemDto> orderItems;
    private LocalDateTime orderDate = LocalDateTime.now();
    private String comment;
}
