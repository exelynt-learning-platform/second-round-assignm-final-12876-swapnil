package com.multigenesys.order_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private  CartRepository cartRepository;
	
	@Autowired
	private  OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public OrderResponse createOrder(Long userId, OrderRequest orderRequest ) {

		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));

		Order order = new Order();
		order.setUserId(userId);

		List<OrderItem> orderItems = new ArrayList();

		double total = 0;

		for (CartItem cartItem : cart.getItems()) {

			OrderItem orderItem = new OrderItem();
			
			Product product = productRepository.findById(cartItem.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			orderItem.setProductId(cartItem.getProductId());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(product.getPrice());
			orderItem.setOrder(order);

			total = total + (cartItem.getQuantity() * product.getPrice());

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

		OrderResponse response = new OrderResponse();
		response.setOrderId(savedOrder.getId());
		response.setUserId(savedOrder.getUserId());
		response.setTotalPrice(savedOrder.getTotalPrice());
		response.setPaymentStatus(savedOrder.getPaymentStatus());
//        response.setItems(savedOrder.getItems());

		List<OrderItem> itemList = new ArrayList<>();

		for (OrderItem item : savedOrder.getItems()) {

			OrderItem newItem = new OrderItem();

			newItem.setId(item.getId());
			newItem.setProductId(item.getProductId());
			newItem.setQuantity(item.getQuantity());
			newItem.setPrice(item.getPrice());

			itemList.add(newItem);
		}

		response.setItems(itemList);

		return response;
	}

	@Override
	public OrderResponse getOrderById(Long orderId) {

		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		OrderResponse response = new OrderResponse();

		response.setOrderId(order.getId());
		response.setUserId(order.getUserId());
		response.setTotalPrice(order.getTotalPrice());
		response.setPaymentStatus(order.getPaymentStatus());
//		response.setItems(order.getItems());
		
		List<OrderItem> itemList = new ArrayList<>();

		for (OrderItem item : order.getItems()) {

		    OrderItem newItem = new OrderItem();

		    newItem.setId(item.getId());
		    newItem.setProductId(item.getProductId());
		    newItem.setQuantity(item.getQuantity());
		    newItem.setPrice(item.getPrice());

		    itemList.add(newItem);
		}

		response.setItems(itemList);

		return response;
	}
}