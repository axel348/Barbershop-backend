package com.barbershop.userservice.exception;

/**
 * El email ya está registrado en el sistema.
 */
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("El email ya está registrado: " + email);
    }
}
