package com.barbershop.bff.controller;

import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.cart.CartItemRequestDto;
import com.barbershop.bff.dto.cart.CartItemResponseDto;
import com.barbershop.bff.dto.cart.CartItemUpdateRequestDto;
import com.barbershop.bff.dto.cart.CartResponseDto;
import com.barbershop.bff.service.CartBffService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bff/cart")
public class CartBffController {

    private final CartBffService cartBffService;

    public CartBffController(CartBffService cartBffService) {
        this.cartBffService = cartBffService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart() {
        return cartBffService.getCart();
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> addItem(
            @Valid @RequestBody CartItemRequestDto request) {
        return cartBffService.addItem(request);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody CartItemUpdateRequestDto request) {
        return cartBffService.updateItem(id, request);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Void>> removeItem(@PathVariable Long id) {
        return cartBffService.removeItem(id);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        return cartBffService.clearCart();
    }
}
