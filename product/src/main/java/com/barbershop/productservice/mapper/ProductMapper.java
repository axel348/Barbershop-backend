package com.barbershop.productservice.mapper;

import com.barbershop.productservice.dto.ProductRequestDto;
import com.barbershop.productservice.dto.ProductResponseDto;
import com.barbershop.productservice.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Convierte entre entidades JPA y DTOs (evita exponer la capa de persistencia).
 */
@Component
public class ProductMapper {

    public ProductResponseDto toResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .brand(product.getBrand())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    public List<ProductResponseDto> toResponseDtoList(List<Product> products) {
        return products.stream().map(this::toResponseDto).toList();
    }

    public Product toEntity(ProductRequestDto request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .brand(request.getBrand())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }

    public void updateEntity(Product product, ProductRequestDto request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
    }
}
