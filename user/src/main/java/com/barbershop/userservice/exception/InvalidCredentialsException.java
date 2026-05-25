package com.barbershop.userservice.exception;

/**
 * Credenciales de login incorrectas (HTTP 401).
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Email o contraseña incorrectos");
    }
}
