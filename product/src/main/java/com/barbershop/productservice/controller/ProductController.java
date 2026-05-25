package com.barbershop.productservice.controller;

import com.barbershop.productservice.dto.ApiResponse;
import com.barbershop.productservice.dto.ProductRequestDto;
import com.barbershop.productservice.dto.ProductResponseDto;
import com.barbershop.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Capa REST: expone los endpoints del catálogo de productos.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /** GET /api/products — Lista todos los productos. */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> products = productService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(products));
    }

    /** GET /api/products/{id} — Obtiene un producto por ID. */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(@PathVariable Long id) {
        ProductResponseDto product = productService.findById(id);
        return ResponseEntity.ok(ApiResponse.ok(product));
    }

    /** GET /api/products/category/{category} — Filtra por categoría. */
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getProductsByCategory(
            @PathVariable String category) {
        List<ProductResponseDto> products = productService.findByCategory(category);
        return ResponseEntity.ok(ApiResponse.ok(products));
    }

    /** POST /api/products — Crea un producto nuevo. */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @Valid @RequestBody ProductRequestDto request) {
        ProductResponseDto created = productService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Producto creado correctamente", created));
    }

    /** PUT /api/products/{id} — Actualiza un producto existente. */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto request) {
        ProductResponseDto updated = productService.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Producto actualizado correctamente", updated));
    }

    /** DELETE /api/products/{id} — Elimina un producto. */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Producto eliminado correctamente"));
    }
}
