package com.barbershop.bff.service;

import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.cart.CartItemRequestDto;
import com.barbershop.bff.dto.cart.CartItemResponseDto;
import com.barbershop.bff.dto.cart.CartItemUpdateRequestDto;
import com.barbershop.bff.dto.cart.CartResponseDto;
import org.springframework.http.ResponseEntity;

public interface CartBffService {

    ResponseEntity<ApiResponse<CartResponseDto>> getCart();

    ResponseEntity<ApiResponse<CartItemResponseDto>> addItem(CartItemRequestDto request);

    ResponseEntity<ApiResponse<CartItemResponseDto>> updateItem(Long id, CartItemUpdateRequestDto request);

    ResponseEntity<ApiResponse<Void>> removeItem(Long id);

    ResponseEntity<ApiResponse<Void>> clearCart();
}
