package com.arturk.customer.service;

import com.arturk.customer.dto.OrderCreatedEvent;
import com.arturk.customer.dto.OrderItemDto;
import com.arturk.customer.dto.PaymentEvent;
import com.arturk.customer.entity.CustomerEntity;
import com.arturk.customer.entity.repository.CustomerRepository;
import com.arturk.customer.enums.PaymentStatusEnum;
import com.arturk.customer.exception.CustomerNotFoundException;
import com.arturk.customer.exception.MoneyNotAvailableException;
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
public class StorageAvailableListener {

    @Value("${kafka.topic-name.payment_event}")
    private String paymentEventTopic;

    private final StorageClient storageClient;
    private final CustomerRepository customerRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(id = "storage_available_listener", topics = {"${kafka.topic-name.storage_available}"},
            groupId = "${kafka.group-id}", concurrency = "3",
            containerFactory = "orderCreatedEventKafkaListenerContainerFactory")
    public void storageAvailableListener(@Payload OrderCreatedEvent event) {
        log.debug("Order created event received: {}", event.getOrderUuid());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setOrderUuid(event.getOrderUuid());
        paymentEvent.setOrderItems(event.getOrderItems());
        try {
            CustomerEntity customer = customerRepository.findById(event.getCustomerId())
                    .orElseThrow(CustomerNotFoundException::new);
            double totalPrice = 0;
            for (OrderItemDto orderItem : event.getOrderItems()) {
                double productPrice = storageClient.getProductPrice(orderItem.getProductId()).getBody(); // todo: change to BigDecimal
                double totalProductPrice = productPrice * orderItem.getQuantity();
                totalPrice += totalProductPrice;
            }
            customer.debit(totalPrice);
            customerRepository.save(customer);
            paymentEvent.setStatus(PaymentStatusEnum.PAID);
            kafkaTemplate.send(paymentEventTopic, event.getOrderUuid().toString(), paymentEvent);
            log.debug("Order created event processed: {}", event.getOrderUuid());
        } catch (MoneyNotAvailableException exception) {
            log.error("MoneyNotAvailableException occurred during processing created order: {}", event.getOrderUuid(), exception);
            paymentEvent.setStatus(PaymentStatusEnum.NOT_PAID);
            kafkaTemplate.send(paymentEventTopic, event.getOrderUuid().toString(), paymentEvent);
            throw exception;
        }
    }

}
