package com.barbershop.productservice.controller;

import com.barbershop.productservice.dto.ProductRequestDto;
import com.barbershop.productservice.dto.ProductResponseDto;
import com.barbershop.productservice.exception.GlobalExceptionHandler;
import com.barbershop.productservice.exception.ProductNotFoundException;
import com.barbershop.productservice.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Archivo: product/src/test/java/com/barbershop/productservice/controller/ProductControllerTest.java
 */
@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private final ProductResponseDto sampleResponse = ProductResponseDto.builder()
            .id(1L)
            .name("Pomada mate")
            .description("Fijación fuerte")
            .category("ESTILO_CABELLO")
            .brand("Suavecito")
            .price(15990)
            .stock(30)
            .build();

    @Test
    @DisplayName("GET /api/products - listar productos")
    void getAllProducts_shouldReturn200() throws Exception {
        when(productService.findAll()).thenReturn(List.of(sampleResponse));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Pomada mate"))
                .andExpect(jsonPath("$.data[0].id").value(1));
    }

    @Test
    @DisplayName("GET /api/products/{id} - buscar por ID")
    void getProductById_shouldReturn200() throws Exception {
        when(productService.findById(1L)).thenReturn(sampleResponse);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.category").value("ESTILO_CABELLO"));
    }

    @Test
    @DisplayName("GET /api/products/{id} - 404 si no existe")
    void getProductById_whenNotFound_shouldReturn404() throws Exception {
        when(productService.findById(99L)).thenThrow(new ProductNotFoundException(99L));

        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("GET /api/products/category/{category} - búsqueda por categoría")
    void getProductsByCategory_shouldReturn200() throws Exception {
        when(productService.findByCategory("ESTILO_CABELLO")).thenReturn(List.of(sampleResponse));

        mockMvc.perform(get("/api/products/category/ESTILO_CABELLO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].category").value("ESTILO_CABELLO"));
    }

    private static final String VALID_PRODUCT_JSON = """
            {
              "name": "Pomada mate",
              "description": "Fijación fuerte",
              "category": "ESTILO_CABELLO",
              "brand": "Suavecito",
              "price": 15990,
              "stock": 30
            }
            """;

    @Test
    @DisplayName("POST /api/products - crear producto")
    void createProduct_shouldReturn201() throws Exception {
        when(productService.create(any(ProductRequestDto.class))).thenReturn(sampleResponse);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_PRODUCT_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Producto creado correctamente"))
                .andExpect(jsonPath("$.data.name").value("Pomada mate"));

        verify(productService).create(any(ProductRequestDto.class));
    }

    private static final String UPDATE_PRODUCT_JSON = """
            {
              "name": "Pomada actualizada",
              "description": "Nueva fórmula",
              "category": "ESTILO_CABELLO",
              "brand": "Suavecito",
              "price": 16990,
              "stock": 25
            }
            """;

    @Test
    @DisplayName("PUT /api/products/{id} - actualizar producto")
    void updateProduct_shouldReturn200() throws Exception {
        ProductResponseDto updated = ProductResponseDto.builder()
                .id(1L)
                .name("Pomada actualizada")
                .description("Nueva fórmula")
                .category("ESTILO_CABELLO")
                .brand("Suavecito")
                .price(16990)
                .stock(25)
                .build();

        when(productService.update(eq(1L), any(ProductRequestDto.class))).thenReturn(updated);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_PRODUCT_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Pomada actualizada"))
                .andExpect(jsonPath("$.data.price").value(16990));
    }

    @Test
    @DisplayName("DELETE /api/products/{id} - eliminar producto")
    void deleteProduct_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Producto eliminado correctamente"));

        verify(productService).deleteById(1L);
    }

    @Test
    @DisplayName("DELETE /api/products/{id} - 404 si no existe")
    void deleteProduct_whenNotFound_shouldReturn404() throws Exception {
        doThrow(new ProductNotFoundException(99L)).when(productService).deleteById(99L);

        mockMvc.perform(delete("/api/products/99"))
                .andExpect(status().isNotFound());
    }
}
