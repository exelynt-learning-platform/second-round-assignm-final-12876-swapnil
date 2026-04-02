package com.multigenesys.order_service.service;

import com.multigenesys.order_service.dto.CartRequest;
import com.multigenesys.order_service.dto.CartResponse;

public interface CartService {

    CartResponse addToCart(Long userId, CartRequest request);

    CartResponse updateCartItem(Long userId, Long itemId, Integer quantity);

    void removeCartItem(Long userId, Long itemId);

    CartResponse getCart(Long userId);
}
