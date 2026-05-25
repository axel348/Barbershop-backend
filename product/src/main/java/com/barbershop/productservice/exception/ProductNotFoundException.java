package com.barbershop.productservice.exception;

/**
 * Se lanza cuando no existe un producto con el identificador solicitado.
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Producto no encontrado con id: " + id);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
