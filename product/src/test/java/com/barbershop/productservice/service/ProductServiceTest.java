package com.barbershop.productservice.service;

import com.barbershop.productservice.dto.ProductRequestDto;
import com.barbershop.productservice.dto.ProductResponseDto;
import com.barbershop.productservice.entity.Product;
import com.barbershop.productservice.exception.ProductNotFoundException;
import com.barbershop.productservice.mapper.ProductMapper;
import com.barbershop.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Archivo: product/src/test/java/com/barbershop/productservice/service/ProductServiceTest.java
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductMapper productMapper;
    private ProductServiceImpl productService;

    private Product sampleProduct;
    private ProductRequestDto sampleRequest;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
        productService = new ProductServiceImpl(productRepository, productMapper);

        sampleProduct = Product.builder()
                .id(1L)
                .name("Pomada mate")
                .description("Fijación fuerte")
                .category("ESTILO_CABELLO")
                .brand("Suavecito")
                .price(15990)
                .stock(30)
                .build();

        sampleRequest = ProductRequestDto.builder()
                .name("Pomada mate")
                .description("Fijación fuerte")
                .category("ESTILO_CABELLO")
                .brand("Suavecito")
                .price(15990)
                .stock(30)
                .build();
    }

    @Test
    @DisplayName("findAll - debe listar todos los productos")
    void findAll_shouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(sampleProduct));

        List<ProductResponseDto> result = productService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pomada mate", result.get(0).getName());
        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("findById - debe encontrar producto por ID")
    void findById_whenExists_shouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        ProductResponseDto result = productService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ESTILO_CABELLO", result.getCategory());
    }

    @Test
    @DisplayName("findById - debe lanzar excepción si no existe")
    void findById_whenNotExists_shouldThrow() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findById(99L));
    }

    @Test
    @DisplayName("findByCategory - debe buscar por categoría")
    void findByCategory_shouldReturnProducts() {
        when(productRepository.findByCategoryIgnoreCase("ESTILO_CABELLO"))
                .thenReturn(List.of(sampleProduct));

        List<ProductResponseDto> result = productService.findByCategory("ESTILO_CABELLO");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ESTILO_CABELLO", result.get(0).getCategory());
    }

    @Test
    @DisplayName("findByCategory - debe lanzar excepción si categoría vacía")
    void findByCategory_whenEmpty_shouldThrow() {
        when(productRepository.findByCategoryIgnoreCase("INEXISTENTE")).thenReturn(List.of());

        assertThrows(ProductNotFoundException.class,
                () -> productService.findByCategory("INEXISTENTE"));
    }

    @Test
    @DisplayName("create - debe crear un producto")
    void create_shouldSaveAndReturnProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        ProductResponseDto result = productService.create(sampleRequest);

        assertNotNull(result);
        assertEquals("Pomada mate", result.getName());
        assertEquals(15990, result.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("update - debe actualizar un producto")
    void update_shouldUpdateAndReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        ProductResponseDto result = productService.update(1L, sampleRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("update - debe lanzar excepción si no existe")
    void update_whenNotExists_shouldThrow() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.update(99L, sampleRequest));
    }

    @Test
    @DisplayName("deleteById - debe eliminar producto existente")
    void deleteById_whenExists_shouldDelete() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteById(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById - debe lanzar excepción si no existe")
    void deleteById_whenNotExists_shouldThrow() {
        when(productRepository.existsById(99L)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteById(99L));
    }
}
