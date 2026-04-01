package com.multigenesys.ecommerce_service.dto;

import java.util.List;
import com.multigenesys.ecommerce_service.entity.OrderItem;

public class OrderResponse {

    private Long orderId;
    private Long userId;
    private Double totalPrice;
    private String paymentStatus;
    private List<OrderItem> items;

    public OrderResponse() {
    }

    public OrderResponse(Long orderId, Long userId, Double totalPrice, String paymentStatus, List<OrderItem> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}