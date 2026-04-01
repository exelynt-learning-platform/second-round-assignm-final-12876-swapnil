package com.multigenesys.ecommerce_service.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.multigenesys.ecommerce_service.dto.ProductRequest;
import com.multigenesys.ecommerce_service.entity.Product;
import com.multigenesys.ecommerce_service.repository.ProductRepository;
import com.multigenesys.ecommerce_service.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testCreateProduct() {

        ProductRequest request = new ProductRequest();
        request.setName("Laptop");
        request.setPrice(50000.0);

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        var response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals("Laptop", response.getName());
    }

    @Test
    void testGetProductById() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        var response = productService.getProductById(1L);

        assertEquals("Laptop", response.getName());
    }
}