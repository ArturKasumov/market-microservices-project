package com.arturk.order.entity;

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

    private LocalDateTime orderDate = LocalDateTime.now();

    private String comment;

    private List<OrderItem> orderItems = new ArrayList();

    public static class OrderItem{
        private Long productId;
        private Integer quantity;
    }
}
