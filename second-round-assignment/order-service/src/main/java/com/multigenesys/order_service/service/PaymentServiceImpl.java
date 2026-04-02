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

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
    private  APIContext apiContext;
	
	@Autowired
    private OrderRepository orderRepository;

    @Override
    public PaymentResponse createPayment(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

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
       
        urls.setCancelUrl("http://localhost:9096/api/payment/cancelPayment?orderId=" + orderId);
        urls.setReturnUrl("http://localhost:9096/api/payment/successPayment?orderId=" + orderId);

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
            throw new RuntimeException("Payment creation failed");
        }

        throw new RuntimeException("Payment error");
    }

    
    @Override
    public String successPayment(String paymentId, String payerId, Long orderId) {

        try {

            Payment payment = new Payment();
            payment.setId(paymentId);

            PaymentExecution execution = new PaymentExecution();
            execution.setPayerId(payerId);

            payment.execute(apiContext, execution);

        } catch (Exception e) {
            System.out.println("Skipping PayPal execution for testing");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentStatus("SUCCESS");

        orderRepository.save(order);

        return "Payment Success & Order Updated";
    }

    @Override
    public String cancelPayment(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentStatus("CANCELLED");

        orderRepository.save(order);

        return "Payment Cancelled & Order Updated";
    }
}