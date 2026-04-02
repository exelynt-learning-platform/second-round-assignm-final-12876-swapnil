package com.multigenesys.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multigenesys.order_service.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}