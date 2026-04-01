package com.multigenesys.ecommerce_service.dto;

import java.util.List;

import com.multigenesys.ecommerce_service.entity.CartItem;

public class CartResponse {

	private Long cartId;
	private Long userId;
	private List<CartItem> items;

	public CartResponse() {
		super();
	}

	public CartResponse(Long cartId, Long userId, List<CartItem> items) {
		super();
		this.cartId = cartId;
		this.userId = userId;
		this.items = items;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "CartResponse [cartId=" + cartId + ", userId=" + userId + ", items=" + items + "]";
	}

}