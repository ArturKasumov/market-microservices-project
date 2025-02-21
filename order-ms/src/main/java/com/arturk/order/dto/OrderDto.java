package com.arturk.order.dto;

import com.arturk.order.enums.OrderStatusEnum;
import com.arturk.order.enums.PaymentStatusEnum;
import com.arturk.order.enums.StorageReservationStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
@Setter
public class OrderDto {

    private UUID orderUuid = UUID.randomUUID();

    private Integer customerId;

    private LocalDateTime createdDate = LocalDateTime.now();

    private String comment;

    private OrderStatusEnum orderStatus;

    private StorageReservationStatus storageReservationStatus;

    private PaymentStatusEnum paymentStatusEnum;

    private List<OrderItemDto> orderItems;
}
