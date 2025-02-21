package com.arturk.order.dto;

import com.arturk.order.enums.PaymentStatusEnum;
import com.arturk.order.enums.StorageReservationStatus;
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
