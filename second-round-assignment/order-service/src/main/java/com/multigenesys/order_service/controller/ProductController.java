package com.multigenesys.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.multigenesys.order_service.dto.ApiResponse;
import com.multigenesys.order_service.dto.ProductRequest;
import com.multigenesys.order_service.dto.ProductResponse;
import com.multigenesys.order_service.service.ProductService;

import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired 
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<ApiResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest request) {

        log.info("User Authentication: {}", SecurityContextHolder.getContext().getAuthentication());

        try {
            ProductResponse response = productService.createProduct(request);

            log.info("Product created successfully: {}", response.getName());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("Product created successfully", "SUCCESS", 201, response));

        } catch (RuntimeException ex) {

            log.error("Error creating product", ex);

            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(ex.getMessage(), "ERROR", 400, null));
        }
    }

    @GetMapping("/getAllProduct")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAll() {

        try {
            List<ProductResponse> list = productService.getAllProducts();

            return ResponseEntity.ok(
                    new ApiResponse<>("Products fetched successfully", "SUCCESS", 200, list)
            );

        } catch (Exception ex) {

            log.error("Error fetching products", ex);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Failed to fetch products", "ERROR", 500, null));
        }
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(@PathVariable Long id) {

        try {
            ProductResponse response = productService.getProductById(id);

            return ResponseEntity.ok(
                    new ApiResponse<>("Product fetched successfully", "SUCCESS", 200, response)
            );

        } catch (RuntimeException ex) {

            log.error("Product not found with id: {}", id);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(ex.getMessage(), "ERROR", 404, null));
        }
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(@PathVariable Long id,
                                                               @RequestBody ProductRequest request) {

        try {
            ProductResponse response = productService.updateProduct(id, request);

            log.info("Product updated: {}", id);

            return ResponseEntity.ok(
                    new ApiResponse<>("Product updated successfully", "SUCCESS", 200, response)
            );

        } catch (RuntimeException ex) {

            log.error("Error updating product: {}", id, ex);

            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(ex.getMessage(), "ERROR", 400, null));
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        try {
            productService.deleteProduct(id);

            log.info("Product deleted: {}", id);

            return ResponseEntity.ok(
                    new ApiResponse<>("Product deleted successfully", "SUCCESS", 200, null)
            );

        } catch (RuntimeException ex) {

            log.error("Error deleting product: {}", id, ex);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(ex.getMessage(), "ERROR", 404, null));
        }
    }
}