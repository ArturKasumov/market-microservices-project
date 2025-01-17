package com.arturk.order.dto;

import com.arturk.order.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDto {

    private Integer customerId;

    private LocalDateTime createdDate = LocalDateTime.now();

    private String comment;

    private OrderStatusEnum orderStatus;

    private List<OrderItemDto> orderItems;
}
