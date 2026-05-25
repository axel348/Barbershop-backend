package com.barbershop.bff.exception;

/**
 * El microservicio no está disponible o no responde (timeout/conexión).
 */
public class MicroserviceUnavailableException extends RuntimeException {

    private final String serviceName;

    public MicroserviceUnavailableException(String serviceName, Throwable cause) {
        super("El microservicio '" + serviceName + "' no está disponible", cause);
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
