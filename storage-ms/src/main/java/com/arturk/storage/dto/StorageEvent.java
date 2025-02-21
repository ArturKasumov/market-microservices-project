package com.arturk.storage.dto;

import com.arturk.storage.enums.StorageReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class StorageEvent {

    private UUID orderUuid;
    private StorageReservationStatus status;
    private List<OrderItemDto> orderItems;
}
