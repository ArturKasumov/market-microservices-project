package com.arturk.storage.service;

import com.arturk.storage.dto.OrderItemDto;
import com.arturk.storage.dto.StorageEvent;
import com.arturk.storage.entity.ProductEntity;
import com.arturk.storage.entity.repository.ProductRepository;
import com.arturk.storage.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageRecoveryEventListener {

    private final ProductRepository productRepository;

    @KafkaListener(id = "storage_recovery_listener", topics = {"${kafka.topic-name.storage_recovery_event}"},
            groupId = "${kafka.group-id}", concurrency = "3",
            containerFactory = "storageEventKafkaListenerContainerFactory")
    public void storageRecoveryEventListener(@Payload StorageEvent event) {
        log.info("Received storageRecoveryEvent for order: {}", event.getOrderUuid());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (OrderItemDto orderItem : event.getOrderItems()) {
            ProductEntity productEntity = productRepository.findById(orderItem.getProductId()).orElseThrow(ProductNotFoundException::new);
            productEntity.increaseQuantity(orderItem.getQuantity());
            productRepository.save(productEntity);
        }
        log.info("Processed storageRecoveryEvent successfully for order: {}", event.getOrderUuid());
    }
}
