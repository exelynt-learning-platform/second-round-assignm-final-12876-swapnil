package com.multigenesys.order_service.service;

import com.multigenesys.order_service.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(Long orderId);

    String successPayment(String paymentId, String payerId, Long orderId);

	String cancelPayment(Long orderId);

}