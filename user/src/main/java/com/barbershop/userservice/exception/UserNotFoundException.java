package com.barbershop.userservice.exception;

/**
 * Se lanza cuando no existe un usuario con el identificador solicitado.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Usuario no encontrado con id: " + id);
    }
}
