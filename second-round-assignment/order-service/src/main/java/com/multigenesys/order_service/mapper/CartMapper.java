package com.multigenesys.order_service.mapper;

import com.multigenesys.order_service.entity.CartItem;

public class CartMapper {

    public static CartItem mapToCartItem(CartItem item) {

        CartItem newItem = new CartItem();
        newItem.setId(item.getId());
        newItem.setProductId(item.getProductId());
        newItem.setQuantity(item.getQuantity());

        return newItem;
    }
}