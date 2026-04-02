package com.multigenesys.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.multigenesys.order_service.dto.ApiResponse;
import com.multigenesys.order_service.dto.OrderRequest;
import com.multigenesys.order_service.dto.OrderResponse;
import com.multigenesys.order_service.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<ApiResponse<OrderResponse>> create(HttpServletRequest request,
                                                             @RequestBody OrderRequest orderRequest){

        Long userId = (Long) request.getAttribute("userId");

        try {

            OrderResponse response = orderService.createOrder(userId, orderRequest);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("Order created successfully","SUCCESS",201,response));

        } catch (RuntimeException ex){

            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(ex.getMessage(),"ERROR",400,null));
        }
    }

    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable Long orderId){

        try {

            OrderResponse response = orderService.getOrderById(orderId);

            return ResponseEntity.ok(
                    new ApiResponse<>("Order fetched successfully","SUCCESS",200,response)
            );

        } catch (RuntimeException ex){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(ex.getMessage(),"ERROR",404,null));
        }
    }
}