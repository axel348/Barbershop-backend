package com.barbershop.cartservice.controller;

import com.barbershop.cartservice.dto.ApiResponse;
import com.barbershop.cartservice.dto.CartItemRequestDto;
import com.barbershop.cartservice.dto.CartItemResponseDto;
import com.barbershop.cartservice.dto.CartItemUpdateRequestDto;
import com.barbershop.cartservice.dto.CartResponseDto;
import com.barbershop.cartservice.service.CartService;
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

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart() {
        return ResponseEntity.ok(ApiResponse.ok(cartService.getCart()));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> addItem(
            @Valid @RequestBody CartItemRequestDto request) {
        CartItemResponseDto created = cartService.addItem(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Producto agregado al carrito", created));
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody CartItemUpdateRequestDto request) {
        CartItemResponseDto updated = cartService.updateItem(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Cantidad actualizada", updated));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiResponse<Void>> removeItem(@PathVariable Long id) {
        cartService.removeItem(id);
        return ResponseEntity.ok(ApiResponse.ok("Ítem eliminado del carrito"));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok(ApiResponse.ok("Carrito vaciado"));
    }
}
