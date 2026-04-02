package com.multigenesys.order_service.mapper;

import com.multigenesys.order_service.entity.OrderItem;

public class OrderMapper {

    public static OrderItem mapToOrderItem(OrderItem item) {
        OrderItem newItem = new OrderItem();
        newItem.setId(item.getId());
        newItem.setProductId(item.getProductId());
        newItem.setQuantity(item.getQuantity());
        newItem.setPrice(item.getPrice());
        return newItem;
    }
}