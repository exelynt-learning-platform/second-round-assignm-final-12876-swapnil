package com.multigenesys.ecommerce_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigenesys.ecommerce_service.dto.ProductRequest;
import com.multigenesys.ecommerce_service.dto.ProductResponse;
import com.multigenesys.ecommerce_service.entity.Product;
import com.multigenesys.ecommerce_service.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        try {
            Product product = new Product();
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setStockQuantity(request.getStockQuantity());
            product.setImageUrl(request.getImageUrl());
            product.setActive(true);

            Product saved = productRepository.save(product);

            ProductResponse response = new ProductResponse();
            response.setId(saved.getId());
            response.setName(saved.getName());
            response.setDescription(saved.getDescription());
            response.setPrice(saved.getPrice());
            response.setStockQuantity(saved.getStockQuantity());
            response.setImageUrl(saved.getImageUrl());
            response.setActive(saved.isActive());
            response.setCreatedAt(saved.getCreatedAt());
            response.setUpdatedAt(saved.getUpdatedAt());

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create product: " + e.getMessage());
        }
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        try {
            List<Product> products = productRepository.findAll();
            List<ProductResponse> responseList = new ArrayList<>();

            for (Product product : products) {

                ProductResponse response = new ProductResponse();
                response.setId(product.getId());
                response.setName(product.getName());
                response.setDescription(product.getDescription());
                response.setPrice(product.getPrice());
                response.setStockQuantity(product.getStockQuantity());
                response.setImageUrl(product.getImageUrl());
                response.setActive(product.isActive());
                response.setCreatedAt(product.getCreatedAt());
                response.setUpdatedAt(product.getUpdatedAt());

                responseList.add(response);
            }

            return responseList;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products: " + e.getMessage());
        }
    }

    @Override
    public ProductResponse getProductById(Long id) {

        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setDescription(product.getDescription());
            response.setPrice(product.getPrice());
            response.setStockQuantity(product.getStockQuantity());
            response.setImageUrl(product.getImageUrl());
            response.setActive(product.isActive());
            response.setCreatedAt(product.getCreatedAt());
            response.setUpdatedAt(product.getUpdatedAt());

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching product: " + e.getMessage());
        }
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setStockQuantity(request.getStockQuantity());
            product.setImageUrl(request.getImageUrl());

            Product updated = productRepository.save(product);

            ProductResponse response = new ProductResponse();
            response.setId(updated.getId());
            response.setName(updated.getName());
            response.setDescription(updated.getDescription());
            response.setPrice(updated.getPrice());
            response.setStockQuantity(updated.getStockQuantity());
            response.setImageUrl(updated.getImageUrl());
            response.setActive(updated.isActive());
            response.setCreatedAt(updated.getCreatedAt());
            response.setUpdatedAt(updated.getUpdatedAt());

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Failed to update product: " + e.getMessage());
        }
    }

    @Override
    public void deleteProduct(Long id) {

        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            productRepository.delete(product);

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product: " + e.getMessage());
        }
    }
}