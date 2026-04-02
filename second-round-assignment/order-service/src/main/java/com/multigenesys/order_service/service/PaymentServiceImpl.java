package com.multigenesys.order_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigenesys.order_service.dto.PaymentResponse;
import com.multigenesys.order_service.entity.Order;
import com.multigenesys.order_service.repository.OrderRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	private APIContext apiContext;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public PaymentResponse createPayment(Long orderId) {

		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(String.valueOf(order.getTotalPrice()));

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		RedirectUrls urls = new RedirectUrls();

		urls.setCancelUrl("http://localhost:9096/api/payment/cancel?orderId=" + orderId);
		urls.setReturnUrl("http://localhost:9096/api/payment/success?orderId=" + orderId);

		payment.setRedirectUrls(urls);

		try {

			Payment createdPayment = payment.create(apiContext);

			for (Links link : createdPayment.getLinks()) {

				if (link.getRel().equals("approval_url")) {

					PaymentResponse response = new PaymentResponse();
					response.setApprovalUrl(link.getHref());
					response.setStatus("REDIRECT");

					return response;
				}
			}

		} catch (Exception e) {

			log.error("Error creating PayPal payment for orderId: {}", orderId, e);
			throw new RuntimeException("Payment creation failed");
		}

		throw new RuntimeException("Payment error");
	}

	@Override
	public String successPayment(String paymentId, String payerId, Long orderId) {

	    Order order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Order not found"));

	    try {
	        Payment payment = new Payment();
	        payment.setId(paymentId);

	        PaymentExecution execution = new PaymentExecution();
	        execution.setPayerId(payerId);

	        Payment executedPayment = payment.execute(apiContext, execution);

	        // ✅ Only mark success AFTER successful execution
	        if (executedPayment != null && "approved".equalsIgnoreCase(executedPayment.getState())) {
	            order.setPaymentStatus("SUCCESS");
	            orderRepository.save(order);

	            log.info("Payment successful for orderId: {}", orderId);
	            return "Payment Success & Order Updated";
	        } else {
	            // ❗ Edge case: execution happened but not approved
	            order.setPaymentStatus("FAILED");
	            orderRepository.save(order);

	            log.warn("Payment not approved for orderId: {}", orderId);
	            return "Payment not approved";
	        }

	    } catch (Exception e) {

	        // ❌ Proper failure handling
	        log.error("PayPal execution failed for orderId: {}", orderId, e);

	        order.setPaymentStatus("FAILED");
	        orderRepository.save(order);

	        throw new RuntimeException("Payment execution failed. Please try again.");
	    }
	}

	@Override
	public String cancelPayment(Long orderId) {

		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		order.setPaymentStatus("CANCELLED");
		orderRepository.save(order);

		return "Payment Cancelled & Order Updated";
	}
}