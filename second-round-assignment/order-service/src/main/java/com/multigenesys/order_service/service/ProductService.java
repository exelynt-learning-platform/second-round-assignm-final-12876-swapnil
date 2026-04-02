package com.multigenesys.order_service.service;

import java.util.List;

import com.multigenesys.order_service.dto.ProductRequest;
import com.multigenesys.order_service.dto.ProductResponse;

public interface ProductService {

	ProductResponse createProduct(ProductRequest request);

	List<ProductResponse> getAllProducts();

	ProductResponse getProductById(Long id);

	ProductResponse updateProduct(Long id, ProductRequest request);

	void deleteProduct(Long id);

}
  