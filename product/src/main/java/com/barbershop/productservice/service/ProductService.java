package com.barbershop.productservice.service;

import com.barbershop.productservice.dto.ProductRequestDto;
import com.barbershop.productservice.dto.ProductResponseDto;

import java.util.List;

/**
 * Contrato de la capa de negocio (Service Layer Pattern).
 */
public interface ProductService {

    List<ProductResponseDto> findAll();

    ProductResponseDto findById(Long id);

    List<ProductResponseDto> findByCategory(String category);

    ProductResponseDto create(ProductRequestDto request);

    ProductResponseDto update(Long id, ProductRequestDto request);

    void deleteById(Long id);
}
