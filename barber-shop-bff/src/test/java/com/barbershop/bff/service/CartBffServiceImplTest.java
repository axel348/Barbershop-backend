package com.barbershop.bff.service;

import com.barbershop.bff.client.CartServiceClient;
import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.cart.CartResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartBffServiceImplTest {

    @Mock
    private CartServiceClient cartServiceClient;

    @InjectMocks
    private CartBffServiceImpl cartBffService;

    @Test
    @DisplayName("getCart - delega en cart-service client")
    void getCart_shouldDelegateToClient() {
        CartResponseDto cart = CartResponseDto.builder()
                .items(List.of())
                .total(0)
                .build();

        ApiResponse<CartResponseDto> response = ApiResponse.<CartResponseDto>builder()
                .success(true)
                .data(cart)
                .timestamp(LocalDateTime.now())
                .build();

        when(cartServiceClient.getCart()).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<ApiResponse<CartResponseDto>> result = cartBffService.getCart();

        assertNotNull(result.getBody());
        assertEquals(0, result.getBody().getData().getTotal());
        verify(cartServiceClient).getCart();
    }
}
