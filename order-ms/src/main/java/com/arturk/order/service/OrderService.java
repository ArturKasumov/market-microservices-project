package com.arturk.order.service;

import com.arturk.order.convertor.OrderConvertor;
import com.arturk.order.dto.OrderCreatedEvent;
import com.arturk.order.dto.OrderDto;
import com.arturk.order.entity.OrderEntity;
import com.arturk.order.entity.repository.OrderRepository;
import com.arturk.order.enums.OrderStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    @Value("${kafka.topic-name.order_created}")
    private String orderCreatedTopic;

    private final OrderRepository orderRepository;
    private final OrderConvertor orderConvertor;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderDto createOrder(OrderDto request) {
        log.debug("Creating order flow started");
        OrderEntity orderEntity = orderConvertor.toOrderEntity(request);
        orderEntity.setOrderStatus(OrderStatusEnum.PENDING);
        orderEntity = orderRepository.save(orderEntity);
        OrderDto orderDto = orderConvertor.toOrderDto(orderEntity);
        kafkaTemplate.send(orderCreatedTopic, orderDto.getOrderUuid().toString(), createOrderCreatedEvent(orderDto));
        log.debug("Creating order flow finished");
        return orderDto;
    }

    private OrderCreatedEvent createOrderCreatedEvent(OrderDto orderDto) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrderUuid(orderDto.getOrderUuid());
        orderCreatedEvent.setOrderItems(orderDto.getOrderItems());
        orderCreatedEvent.setCustomerId(Long.valueOf(orderDto.getCustomerId()));
        return orderCreatedEvent;
    }
}
