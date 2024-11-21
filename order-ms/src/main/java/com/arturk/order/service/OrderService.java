package com.arturk.order.service;

import com.arturk.order.convertor.OrderConvertor;
import com.arturk.order.dto.OrderDto;
import com.arturk.order.entity.OrderEntity;
import com.arturk.order.entity.repository.OrderRepository;
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

    public void createOrder(OrderDto order) {
        OrderEntity orderEntity = orderConvertor.toOrder(order);
        orderRepository.save(orderEntity);
    }
}
