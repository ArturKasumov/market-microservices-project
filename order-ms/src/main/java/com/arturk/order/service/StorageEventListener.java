package com.arturk.order.service;

import com.arturk.order.dto.StorageEvent;
import com.arturk.order.entity.OrderEntity;
import com.arturk.order.entity.repository.OrderRepository;
import com.arturk.order.enums.OrderStatusEnum;
import com.arturk.order.enums.StorageReservationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageEventListener {

    private final OrderRepository orderRepository;

    @KafkaListener(id = "storage_event_listener", topics = {"${kafka.topic-name.storage_event}"},
            groupId = "${kafka.group-id}", concurrency = "3",
            containerFactory = "storageEventKafkaListenerContainerFactory")
    public void storageEventListener(@Payload StorageEvent event) {
        log.debug("Received storage event for order: {}", event.getOrderUuid());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        OrderEntity entity = orderRepository.findByOrderUuid(event.getOrderUuid());
        entity.setStorageReservationStatus(event.getStatus());
        if (StorageReservationStatus.OUT_OF_STOCK.equals(event.getStatus())) {
            entity.setOrderStatus(OrderStatusEnum.FAILED);
        }
        orderRepository.save(entity);
        log.debug("Processed storage event for order: {}", event.getOrderUuid());
    }
}
