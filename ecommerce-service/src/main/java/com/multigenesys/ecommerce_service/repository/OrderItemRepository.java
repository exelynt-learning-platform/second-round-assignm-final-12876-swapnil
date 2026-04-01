package com.multigenesys.ecommerce_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.multigenesys.ecommerce_service.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}