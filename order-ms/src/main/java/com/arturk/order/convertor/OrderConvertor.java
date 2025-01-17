package com.arturk.order.convertor;

import com.arturk.order.dto.OrderDto;
import com.arturk.order.dto.OrderItemDto;
import com.arturk.order.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class OrderConvertor {

    @Autowired
    private OrderItemConvertor orderItemConvertor;

    @Mappings({
            @Mapping(target = "orderItems", source = "orderItems", qualifiedByName = "toOrderItems")
    })
    public abstract OrderEntity toOrderEntity(OrderDto source);

    @Mappings({
            @Mapping(target = "orderItems", source = "orderItems", qualifiedByName = "fromOrderItems")
    })
    public abstract OrderDto toOrderDto(OrderEntity source);

    @Named("toOrderItems")
    public List<OrderEntity.OrderItem> toOrderItems(List<OrderItemDto> orderItemDtos) {
        return orderItemDtos
                .stream()
                .map(orderItemDto -> orderItemConvertor.toOrderItem(orderItemDto))
                .collect(Collectors.toList());
    }

    @Named("fromOrderItems")
    public List<OrderItemDto> fromOrderItems(List<OrderEntity.OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(orderItem -> orderItemConvertor.toOrderItemDto(orderItem))
                .collect(Collectors.toList());
    }
}

