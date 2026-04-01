package com.multigenesys.ecommerce_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.multigenesys.ecommerce_service.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}