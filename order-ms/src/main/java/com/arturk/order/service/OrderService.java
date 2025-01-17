package com.arturk.order.service;

import com.arturk.order.convertor.OrderConvertor;
import com.arturk.order.dto.OrderDto;
import com.arturk.order.entity.OrderEntity;
import com.arturk.order.entity.repository.OrderRepository;
import com.arturk.order.enums.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderConvertor orderConvertor;

    public OrderDto createOrder(OrderDto request) {
        log.debug("Creating order flow started");
        OrderEntity orderEntity = orderConvertor.toOrderEntity(request);
        orderEntity.setOrderStatus(OrderStatusEnum.PENDING);
        orderEntity = orderRepository.save(orderEntity);
        OrderDto response = orderConvertor.toOrderDto(orderEntity);
        log.debug("Creating order flow finished");
        return response;
    }
}
