package com.barbershop.bff.controller;

import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.product.ProductRequestDto;
import com.barbershop.bff.dto.product.ProductResponseDto;
import com.barbershop.bff.service.ProductBffService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Archivo: barber-shop-bff/src/test/java/com/barbershop/bff/controller/BffProductControllerTest.java
 */
@WebMvcTest(ProductBffController.class)
class BffProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductBffService productBffService;

    private final ProductResponseDto sampleProduct = ProductResponseDto.builder()
            .id(1L)
            .name("Pomada mate")
            .description("Fijación fuerte")
            .category("ESTILO_CABELLO")
            .brand("Suavecito")
            .price(15990)
            .stock(30)
            .build();

    private ApiResponse<List<ProductResponseDto>> listResponse() {
        return ApiResponse.<List<ProductResponseDto>>builder()
                .success(true)
                .data(List.of(sampleProduct))
                .timestamp(LocalDateTime.now())
                .build();
    }

    private ApiResponse<ProductResponseDto> singleResponse() {
        return ApiResponse.<ProductResponseDto>builder()
                .success(true)
                .data(sampleProduct)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("GET /bff/products - consume listado de productos")
    void getAllProducts_shouldReturn200() throws Exception {
        when(productBffService.findAll())
                .thenReturn(ResponseEntity.ok(listResponse()));

        mockMvc.perform(get("/bff/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Pomada mate"));

        verify(productBffService).findAll();
    }

    @Test
    @DisplayName("GET /bff/products/{id} - consume producto por ID")
    void getProductById_shouldReturn200() throws Exception {
        when(productBffService.findById(1L))
                .thenReturn(ResponseEntity.ok(singleResponse()));

        mockMvc.perform(get("/bff/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1));

        verify(productBffService).findById(1L);
    }

    @Test
    @DisplayName("GET /bff/products/category/{category} - consume por categoría")
    void getProductsByCategory_shouldReturn200() throws Exception {
        when(productBffService.findByCategory("ESTILO_CABELLO"))
                .thenReturn(ResponseEntity.ok(listResponse()));

        mockMvc.perform(get("/bff/products/category/ESTILO_CABELLO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].category").value("ESTILO_CABELLO"));
    }

    private static final String PRODUCT_JSON = """
            {
              "name": "Pomada mate",
              "description": "Fijación fuerte",
              "category": "ESTILO_CABELLO",
              "brand": "Suavecito",
              "price": 15990,
              "stock": 30
            }
            """;

    private static final String UPDATE_PRODUCT_JSON = """
            {
              "name": "Pomada actualizada",
              "description": "Nueva",
              "category": "ESTILO_CABELLO",
              "brand": "Suavecito",
              "price": 16990,
              "stock": 20
            }
            """;

    @Test
    @DisplayName("POST /bff/products - consume creación de producto")
    void createProduct_shouldReturn201() throws Exception {
        ApiResponse<ProductResponseDto> created = ApiResponse.<ProductResponseDto>builder()
                .success(true)
                .message("Producto creado correctamente")
                .data(sampleProduct)
                .timestamp(LocalDateTime.now())
                .build();

        when(productBffService.create(any(ProductRequestDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(created));

        mockMvc.perform(post("/bff/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PRODUCT_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Pomada mate"));
    }

    @Test
    @DisplayName("PUT /bff/products/{id} - consume actualización")
    void updateProduct_shouldReturn200() throws Exception {
        when(productBffService.update(eq(1L), any(ProductRequestDto.class)))
                .thenReturn(ResponseEntity.ok(singleResponse()));

        mockMvc.perform(put("/bff/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_PRODUCT_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @DisplayName("DELETE /bff/products/{id} - consume eliminación")
    void deleteProduct_shouldReturn200() throws Exception {
        ApiResponse<Void> deleted = ApiResponse.<Void>builder()
                .success(true)
                .message("Producto eliminado correctamente")
                .timestamp(LocalDateTime.now())
                .build();

        when(productBffService.delete(1L)).thenReturn(ResponseEntity.ok(deleted));

        mockMvc.perform(delete("/bff/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Producto eliminado correctamente"));

        verify(productBffService).delete(1L);
    }
}
