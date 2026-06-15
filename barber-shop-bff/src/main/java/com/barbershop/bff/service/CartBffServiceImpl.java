package com.barbershop.bff.service;

import com.barbershop.bff.client.CartServiceClient;
import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.cart.CartItemRequestDto;
import com.barbershop.bff.dto.cart.CartItemResponseDto;
import com.barbershop.bff.dto.cart.CartItemUpdateRequestDto;
import com.barbershop.bff.dto.cart.CartResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartBffServiceImpl implements CartBffService {

    private final CartServiceClient cartServiceClient;

    public CartBffServiceImpl(CartServiceClient cartServiceClient) {
        this.cartServiceClient = cartServiceClient;
    }

    @Override
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart() {
        return cartServiceClient.getCart();
    }

    @Override
    public ResponseEntity<ApiResponse<CartItemResponseDto>> addItem(CartItemRequestDto request) {
        return cartServiceClient.addItem(request);
    }

    @Override
    public ResponseEntity<ApiResponse<CartItemResponseDto>> updateItem(
            Long id,
            CartItemUpdateRequestDto request) {
        return cartServiceClient.updateItem(id, request);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> removeItem(Long id) {
        return cartServiceClient.removeItem(id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        return cartServiceClient.clearCart();
    }
}
