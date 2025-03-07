package com.arturk.order.controller;

import com.arturk.order.dto.OrderDto;
import com.arturk.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v4/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

}
