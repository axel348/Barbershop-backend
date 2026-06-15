package com.barbershop.bff.controller;

import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.product.ProductRequestDto;
import com.barbershop.bff.dto.product.ProductResponseDto;
import com.barbershop.bff.service.ProductBffService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BFF: operaciones de administración de productos (delega en product-service).
 */
@RestController
@RequestMapping("/bff/admin/products")
public class AdminProductBffController {

    private final ProductBffService productBffService;

    public AdminProductBffController(ProductBffService productBffService) {
        this.productBffService = productBffService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @Valid @RequestBody ProductRequestDto request) {
        return productBffService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto request) {
        return productBffService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        return productBffService.delete(id);
    }
}
