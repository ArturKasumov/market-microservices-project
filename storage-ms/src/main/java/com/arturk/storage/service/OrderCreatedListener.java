package com.arturk.storage.service;

import com.arturk.storage.dto.OrderCreatedEvent;
import com.arturk.storage.dto.OrderItemDto;
import com.arturk.storage.dto.StorageEvent;
import com.arturk.storage.entity.ProductEntity;
import com.arturk.storage.entity.repository.ProductRepository;
import com.arturk.storage.enums.StorageReservationStatus;
import com.arturk.storage.exception.NotEnoughAvailableProductException;
import com.arturk.storage.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCreatedListener {

    @Value("${kafka.topic-name.storage_event}")
    private String storageEventTopic;

    @Value("${kafka.topic-name.storage_available}")
    private String storageAvailableTopic;

    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(id = "order_created_listener", topics = {"${kafka.topic-name.order_created}"},
            groupId = "${kafka.group-id}", concurrency = "3",
            containerFactory = "orderCreatedKafkaListenerContainerFactory")
    @Transactional(propagation = Propagation.REQUIRED)
    public void orderCreatedEventListener(@Payload OrderCreatedEvent event) {
        log.debug("Order created event received in storage ms for order: {}", event.getOrderUuid());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        StorageEvent storageEvent = new StorageEvent();
        storageEvent.setOrderUuid(event.getOrderUuid());
        storageEvent.setOrderItems(event.getOrderItems());
        try {
            for (OrderItemDto orderItem : event.getOrderItems()) {
                ProductEntity productEntity = productRepository.findById(orderItem.getProductId()).orElseThrow(ProductNotFoundException::new);
                productEntity.decreaseQuantity(orderItem.getQuantity());
                productRepository.save(productEntity);
            }
            storageEvent.setStatus(StorageReservationStatus.AVAILABLE);
            kafkaTemplate.send(storageAvailableTopic, event.getOrderUuid().toString(), event);
            kafkaTemplate.send(storageEventTopic, event.getOrderUuid().toString(), storageEvent);
            log.debug("Order created event processed for order: {}", event.getOrderUuid());
        } catch (NotEnoughAvailableProductException exception) {
            log.error("NotEnoughAvailableProductException occurred during processing order created event for order: {}", event.getOrderUuid(), exception);
            storageEvent.setStatus(StorageReservationStatus.OUT_OF_STOCK);
            kafkaTemplate.send(storageEventTopic, event.getOrderUuid().toString(), storageEvent);
            throw exception;
        }
    }
}
