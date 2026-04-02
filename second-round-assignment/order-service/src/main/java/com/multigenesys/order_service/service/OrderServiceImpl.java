package com.multigenesys.order_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multigenesys.order_service.dto.OrderRequest;
import com.multigenesys.order_service.dto.OrderResponse;
import com.multigenesys.order_service.entity.Cart;
import com.multigenesys.order_service.entity.CartItem;
import com.multigenesys.order_service.entity.Order;
import com.multigenesys.order_service.entity.OrderItem;
import com.multigenesys.order_service.entity.Product;
import com.multigenesys.order_service.repository.CartRepository;
import com.multigenesys.order_service.repository.OrderRepository;
import com.multigenesys.order_service.repository.ProductRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest orderRequest) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // ✅ Proper validation for null or empty cart
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUserId(userId);

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem cartItem : cart.getItems()) {

            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + cartItem.getProductId()));

            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            // ✅ Mapping directly without OrderMapper
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            total += cartItem.getQuantity() * product.getPrice();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);
        order.setPaymentStatus("PENDING");
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setCity(orderRequest.getCity());
        order.setState(orderRequest.getState());
        order.setZipCode(orderRequest.getZipCode());
        order.setCountry(orderRequest.getCountry());

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        // ✅ Mapping directly in response
        OrderResponse response = new OrderResponse();
        response.setOrderId(savedOrder.getId());
        response.setUserId(savedOrder.getUserId());
        response.setTotalPrice(savedOrder.getTotalPrice());
        response.setPaymentStatus(savedOrder.getPaymentStatus());

        List<OrderResponse.OrderItemResponse> itemList = new ArrayList<>();
        for (OrderItem item : savedOrder.getItems()) {
            OrderResponse.OrderItemResponse itemResponse = new OrderResponse.OrderItemResponse();
            itemResponse.setItemId(item.getId());
            itemResponse.setProductId(item.getProductId());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPrice(item.getPrice());
            itemList.add(itemResponse);
        }
        response.setItems(itemList);

        return response;
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setUserId(order.getUserId());
        response.setTotalPrice(order.getTotalPrice());
        response.setPaymentStatus(order.getPaymentStatus());

        List<OrderResponse.OrderItemResponse> itemList = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            OrderResponse.OrderItemResponse itemResponse = new OrderResponse.OrderItemResponse();
            itemResponse.setItemId(item.getId());
            itemResponse.setProductId(item.getProductId());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPrice(item.getPrice());
            itemList.add(itemResponse);
        }
        response.setItems(itemList);

        return response;
    }
}