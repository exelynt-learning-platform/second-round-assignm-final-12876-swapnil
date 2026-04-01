package com.multigenesys.ecommerce_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.multigenesys.ecommerce_service.dto.CartRequest;
import com.multigenesys.ecommerce_service.dto.CartResponse;
import com.multigenesys.ecommerce_service.entity.Cart;
import com.multigenesys.ecommerce_service.entity.CartItem;
import com.multigenesys.ecommerce_service.repository.CartItemRepository;
import com.multigenesys.ecommerce_service.repository.CartRepository;
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartResponse addToCart(Long userId, CartRequest request) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });

        CartItem item = new CartItem();
        item.setProductId(request.getProductId());
        item.setQuantity(request.getQuantity());
        item.setCart(cart);

        cartItemRepository.save(item);

        return buildCartResponse(cart);
    }

    @Override
    public CartResponse updateCartItem(Long userId, Long itemId, Integer quantity) {

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getCart().getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        return buildCartResponse(item.getCart());
    }

    @Override
    public void removeCartItem(Long userId, Long itemId) {

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getCart().getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        cartItemRepository.delete(item);
    }

    @Override
    public CartResponse getCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return buildCartResponse(cart);
    }

    // ✅ Manual mapping (NO streams, NO separate mapper class)
    private CartResponse buildCartResponse(Cart cart) {

        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        response.setUserId(cart.getUserId());

        List<CartItem> itemList = new ArrayList();

        for (CartItem item : cart.getItems()) {
            CartItem newItem = new CartItem();

            newItem.setId(item.getId());
            newItem.setProductId(item.getProductId());
            newItem.setQuantity(item.getQuantity());

            itemList.add(newItem);
        }

        response.setItems(itemList);

        return response;
    }
}