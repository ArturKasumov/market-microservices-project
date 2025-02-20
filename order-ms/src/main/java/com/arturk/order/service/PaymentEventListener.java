package com.arturk.order.service;

import com.arturk.order.dto.PaymentEvent;
import com.arturk.order.dto.StorageEvent;
import com.arturk.order.entity.OrderEntity;
import com.arturk.order.entity.repository.OrderRepository;
import com.arturk.order.enums.OrderStatusEnum;
import com.arturk.order.enums.PaymentStatusEnum;
import com.arturk.order.enums.StorageReservationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventListener {

    @Value("${kafka.topic-name.storage_recovery_event}")
    private String storageRecoveryEvent;

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(id = "payment_event_listener", topics = {"${kafka.topic-name.payment_event}"},
            groupId = "${kafka.group-id}", concurrency = "3",
            containerFactory = "paymentEventKafkaListenerContainerFactory")
    public void paymentEventListener(@Payload PaymentEvent event) {
        log.debug("Received payment event for order: {}", event.getOrderUuid());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        OrderEntity entity = orderRepository.findByOrderUuid(event.getOrderUuid());
        entity.setPaymentStatus(event.getStatus());
        if (PaymentStatusEnum.PAID.equals(event.getStatus())) {
            entity.setOrderStatus(OrderStatusEnum.CREATED);
        } else if (PaymentStatusEnum.NOT_PAID.equals(event.getStatus())) {
            entity.setOrderStatus(OrderStatusEnum.FAILED);
            kafkaTemplate.send(storageRecoveryEvent, event.getOrderUuid().toString(), createStorageEvent(event));
        }
        orderRepository.save(entity);
        log.debug("Processed payment event for order: {}", event.getOrderUuid());
    }

    private StorageEvent createStorageEvent(PaymentEvent event) {
        StorageEvent storageEvent = new StorageEvent();
        storageEvent.setOrderUuid(event.getOrderUuid());
        storageEvent.setOrderItems(event.getOrderItems());
        return storageEvent;
    }
}
