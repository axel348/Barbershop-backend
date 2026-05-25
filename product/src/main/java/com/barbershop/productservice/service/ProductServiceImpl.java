package com.barbershop.productservice.service;

import com.barbershop.productservice.dto.ProductRequestDto;
import com.barbershop.productservice.dto.ProductResponseDto;
import com.barbershop.productservice.entity.Product;
import com.barbershop.productservice.exception.ProductNotFoundException;
import com.barbershop.productservice.mapper.ProductMapper;
import com.barbershop.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de la lógica CRUD de productos.
 * Usa inyección por constructor (recomendado por Spring).
 */
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productMapper.toResponseDtoList(productRepository.findAll());
    }

    @Override
    public ProductResponseDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> findByCategory(String category) {
        List<Product> products = productRepository.findByCategoryIgnoreCase(category);
        if (products.isEmpty()) {
            throw new ProductNotFoundException(
                    "No se encontraron productos en la categoría: " + category);
        }
        return productMapper.toResponseDtoList(products);
    }

    @Override
    @Transactional
    public ProductResponseDto create(ProductRequestDto request) {
        Product product = productMapper.toEntity(request);
        Product saved = productRepository.save(product);
        return productMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public ProductResponseDto update(Long id, ProductRequestDto request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productMapper.updateEntity(product, request);
        Product updated = productRepository.save(product);
        return productMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}
