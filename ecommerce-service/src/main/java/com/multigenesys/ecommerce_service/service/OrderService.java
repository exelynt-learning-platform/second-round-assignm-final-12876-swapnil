package com.multigenesys.ecommerce_service.service;

import com.multigenesys.ecommerce_service.dto.OrderRequest;
import com.multigenesys.ecommerce_service.dto.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(Long userId, OrderRequest orderRequest);

    OrderResponse getOrderById(Long orderId);


}