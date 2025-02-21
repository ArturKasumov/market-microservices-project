package com.arturk.storage.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderCreatedEvent {

    private UUID orderUuid;
    private List<OrderItemDto> orderItems;
    private Long customerId;
}
