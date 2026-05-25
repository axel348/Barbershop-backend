package com.barbershop.orderservice.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Order no encontrado con id: " + id);
    }
}
