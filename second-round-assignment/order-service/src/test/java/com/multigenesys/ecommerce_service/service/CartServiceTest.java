package com.multigenesys.ecommerce_service.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.multigenesys.order_service.dto.CartRequest;
import com.multigenesys.order_service.entity.Cart;
import com.multigenesys.order_service.repository.CartItemRepository;
import com.multigenesys.order_service.repository.CartRepository;
import com.multigenesys.order_service.service.CartServiceImpl;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void testAddToCart() {

        Long userId = 1L;

        Cart cart = new Cart();
        cart.setUserId(userId);

        CartRequest request = new CartRequest();
        request.setProductId(10L);
        request.setQuantity(2);

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));

        var response = cartService.addToCart(userId, request);

        assertNotNull(response);
        assertEquals(userId, response.getUserId());
    }
}