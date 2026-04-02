package com.multigenesys.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.multigenesys.order_service.dto.ApiResponse;
import com.multigenesys.order_service.dto.PaymentResponse;
import com.multigenesys.order_service.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Autowired
    private PaymentService paymentService;

    @PostMapping("/createPayment/{orderId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(@PathVariable Long orderId) {

        try {

            PaymentResponse response = paymentService.createPayment(orderId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("Payment created",
                            "SUCCESS",
                            201,
                            response));

        } catch (RuntimeException ex) {

            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(ex.getMessage(),
                            "ERROR",
                            400,
                            null));
        }
    }

    @GetMapping("/successPayment")
    public String success(@RequestParam String paymentId,
                          @RequestParam String PayerID,
                          @RequestParam Long orderId) {

        return paymentService.successPayment(paymentId, PayerID,orderId);
    }

    @GetMapping("/cancelPayment")
    public String cancel(@RequestParam Long orderId) {

        return paymentService.cancelPayment(orderId);
    }
}