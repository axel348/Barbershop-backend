package com.barbershop.bff.service;

import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.product.ProductRequestDto;
import com.barbershop.bff.dto.product.ProductResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductBffService {

    ResponseEntity<ApiResponse<List<ProductResponseDto>>> findAll();

    ResponseEntity<ApiResponse<ProductResponseDto>> findById(Long id);

    ResponseEntity<ApiResponse<List<ProductResponseDto>>> findByCategory(String category);

    ResponseEntity<ApiResponse<ProductResponseDto>> create(ProductRequestDto request);

    ResponseEntity<ApiResponse<ProductResponseDto>> update(Long id, ProductRequestDto request);

    ResponseEntity<ApiResponse<Void>> delete(Long id);
}
