package com.barbershop.bff.controller;

import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.cart.CartItemResponseDto;
import com.barbershop.bff.dto.cart.CartResponseDto;
import com.barbershop.bff.service.CartBffService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartBffController.class)
class CartBffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartBffService cartBffService;

    @Test
    @DisplayName("GET /bff/cart - obtiene carrito")
    void getCart_shouldReturn200() throws Exception {
        CartItemResponseDto item = CartItemResponseDto.builder()
                .id(1L)
                .productId(10L)
                .productName("Pomada mate")
                .price(15990)
                .quantity(1)
                .subtotal(15990)
                .build();

        CartResponseDto cart = CartResponseDto.builder()
                .items(List.of(item))
                .total(15990)
                .build();

        ApiResponse<CartResponseDto> response = ApiResponse.<CartResponseDto>builder()
                .success(true)
                .data(cart)
                .timestamp(LocalDateTime.now())
                .build();

        when(cartBffService.getCart()).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(get("/bff/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.total").value(15990));

        verify(cartBffService).getCart();
    }

    @Test
    @DisplayName("POST /bff/cart/items - agrega ítem")
    void addItem_shouldReturn201() throws Exception {
        CartItemResponseDto item = CartItemResponseDto.builder()
                .id(1L)
                .productId(10L)
                .productName("Pomada mate")
                .price(15990)
                .quantity(1)
                .subtotal(15990)
                .build();

        ApiResponse<CartItemResponseDto> response = ApiResponse.<CartItemResponseDto>builder()
                .success(true)
                .data(item)
                .timestamp(LocalDateTime.now())
                .build();

        when(cartBffService.addItem(any())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));

        mockMvc.perform(post("/bff/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productId": 10,
                                  "productName": "Pomada mate",
                                  "price": 15990,
                                  "quantity": 1
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.productName").value("Pomada mate"));
    }
}
