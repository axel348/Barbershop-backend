package com.barbershop.cartservice.service;

import com.barbershop.cartservice.dto.CartItemRequestDto;
import com.barbershop.cartservice.dto.CartItemResponseDto;
import com.barbershop.cartservice.dto.CartItemUpdateRequestDto;
import com.barbershop.cartservice.dto.CartResponseDto;

public interface CartService {

    CartResponseDto getCart();

    CartItemResponseDto addItem(CartItemRequestDto request);

    CartItemResponseDto updateItem(Long id, CartItemUpdateRequestDto request);

    void removeItem(Long id);

    void clearCart();
}
