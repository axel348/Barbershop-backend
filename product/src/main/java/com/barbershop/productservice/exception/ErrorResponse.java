package com.barbershop.productservice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Respuesta JSON uniforme para errores HTTP.
 *
 * <pre>
 * {
 *   "success": false,
 *   "message": "Producto no encontrado con id: 99",
 *   "status": 404,
 *   "timestamp": "2026-05-23T12:00:00",
 *   "path": "/api/products/99"
 * }
 * </pre>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private boolean success;
    private String message;
    private Integer status;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public static ErrorResponse of(int httpStatus, String message, String path) {
        return ErrorResponse.builder()
                .success(false)
                .message(message)
                .status(httpStatus)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse validation(String path, Map<String, String> fieldErrors) {
        return ErrorResponse.builder()
                .success(false)
                .message("Error de validación en los datos enviados")
                .status(400)
                .path(path)
                .timestamp(LocalDateTime.now())
                .errors(fieldErrors)
                .build();
    }
}
