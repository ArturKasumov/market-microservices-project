package com.arturk.order.convertor;

import com.arturk.order.dto.OrderItemDto;
import com.arturk.order.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class OrderItemConvertor {

    abstract OrderEntity.OrderItem toOrderItem(OrderItemDto source);

    abstract OrderItemDto toOrderItemDto(OrderEntity.OrderItem source);
}

