package com.multigenesys.order_service.dto;

public class CartRequest {
	private Long productId;
	private Integer quantity;

	public CartRequest() {
		super();
	}

	public CartRequest(Long productId, Integer quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartRequest [productId=" + productId + ", quantity=" + quantity + "]";
	}

}