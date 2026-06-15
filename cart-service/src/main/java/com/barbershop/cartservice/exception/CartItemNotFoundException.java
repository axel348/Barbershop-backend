package com.barbershop.cartservice.exception;

public class CartItemNotFoundException extends RuntimeException {

    public CartItemNotFoundException(Long id) {
        super("Ítem de carrito no encontrado con id: " + id);
    }
}
