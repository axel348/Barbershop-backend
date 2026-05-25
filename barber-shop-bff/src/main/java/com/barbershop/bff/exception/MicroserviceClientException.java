package com.barbershop.bff.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

/**
 * Error HTTP devuelto por un microservicio (4xx/5xx).
 */
@Getter
public class MicroserviceClientException extends RuntimeException {

    private final String serviceName;
    private final HttpStatusCode statusCode;
    private final ErrorResponse errorBody;

    public MicroserviceClientException(
            String serviceName,
            HttpStatusCode statusCode,
            ErrorResponse errorBody) {
        super(errorBody != null && errorBody.getMessage() != null
                ? errorBody.getMessage()
                : "Error al comunicarse con " + serviceName);
        this.serviceName = serviceName;
        this.statusCode = statusCode;
        this.errorBody = errorBody;
    }
}
