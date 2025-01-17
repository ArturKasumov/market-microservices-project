package com.arturk.order.entity;

import com.arturk.order.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
@Setter
@Getter
public class OrderEntity {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private Integer customerId;

    private LocalDateTime createdDate = LocalDateTime.now();

    private String comment;

    private OrderStatusEnum orderStatus;

    private List<OrderItem> orderItems = new ArrayList<>();

    @Setter
    @Getter
    public static class OrderItem{
        private Long productId;
        private Integer quantity;
    }
}
