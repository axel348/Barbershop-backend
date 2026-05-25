package com.barbershop.bff.service;

import com.barbershop.bff.client.ProductServiceClient;
import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.product.ProductRequestDto;
import com.barbershop.bff.dto.product.ProductResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Layer: orquesta llamadas al product-service vía el cliente HTTP.
 */
@Service
public class ProductBffServiceImpl implements ProductBffService {

    private final ProductServiceClient productServiceClient;

    public ProductBffServiceImpl(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    @Override
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> findAll() {
        return productServiceClient.findAll();
    }

    @Override
    public ResponseEntity<ApiResponse<ProductResponseDto>> findById(Long id) {
        return productServiceClient.findById(id);
    }

    @Override
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> findByCategory(String category) {
        return productServiceClient.findByCategory(category);
    }

    @Override
    public ResponseEntity<ApiResponse<ProductResponseDto>> create(ProductRequestDto request) {
        return productServiceClient.create(request);
    }

    @Override
    public ResponseEntity<ApiResponse<ProductResponseDto>> update(Long id, ProductRequestDto request) {
        return productServiceClient.update(id, request);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(Long id) {
        return productServiceClient.delete(id);
    }
}
