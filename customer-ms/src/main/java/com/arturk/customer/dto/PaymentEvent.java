package com.arturk.customer.dto;

import com.arturk.customer.enums.PaymentStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PaymentEvent {

    private UUID orderUuid;
    private PaymentStatusEnum status;
    private List<OrderItemDto> orderItems;
}
