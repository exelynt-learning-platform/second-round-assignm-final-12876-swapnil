package com.multigenesys.order_service.service;

import com.multigenesys.order_service.dto.OrderRequest;
import com.multigenesys.order_service.dto.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(Long userId, OrderRequest orderRequest);

    OrderResponse getOrderById(Long orderId);


}