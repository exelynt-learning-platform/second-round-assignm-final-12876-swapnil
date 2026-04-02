package com.multigenesys.ecommerce_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.multigenesys.order_service.entity.Order;
import com.multigenesys.order_service.repository.OrderRepository;
import com.multigenesys.order_service.service.PaymentServiceImpl;
import com.paypal.base.rest.APIContext;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private APIContext apiContext;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void testCancelPayment() {

        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        String result = paymentService.cancelPayment(1L);

        assertEquals("Payment Cancelled & Order Updated", result);
    }
}