package com.arturk.order.entity.repository;

import com.arturk.order.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, String> {

    OrderEntity findByOrderUuid(UUID orderUuid);
}
