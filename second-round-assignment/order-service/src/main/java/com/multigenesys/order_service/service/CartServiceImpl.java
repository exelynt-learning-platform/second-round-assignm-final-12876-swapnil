package com.multigenesys.order_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.multigenesys.order_service.dto.CartRequest;
import com.multigenesys.order_service.dto.CartResponse;
import com.multigenesys.order_service.entity.Cart;
import com.multigenesys.order_service.entity.CartItem;
import com.multigenesys.order_service.entity.Product;
import com.multigenesys.order_service.repository.CartItemRepository;
import com.multigenesys.order_service.repository.CartRepository;
import com.multigenesys.order_service.repository.ProductRepository;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartResponse addToCart(Long userId, CartRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });

        CartItem item = new CartItem();
        item.setProductId(product.getId());
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

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
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

    private CartResponse buildCartResponse(Cart cart) {

        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        response.setUserId(cart.getUserId());

        List<CartItem> itemList = new ArrayList<>();

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