package com.barbershop.bff.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MicroserviceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleMicroserviceUnavailable(
            MicroserviceUnavailableException ex,
            HttpServletRequest request) {
        ErrorResponse body = ErrorResponse.of(503, ex.getMessage(), request.getRequestURI());
        body.setService(ex.getServiceName());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body);
    }

    @ExceptionHandler(MicroserviceClientException.class)
    public ResponseEntity<ErrorResponse> handleMicroserviceClient(
            MicroserviceClientException ex,
            HttpServletRequest request) {
        if (ex.getErrorBody() != null) {
            ErrorResponse downstream = ex.getErrorBody();
            downstream.setService(ex.getServiceName());
            if (downstream.getPath() == null) {
                downstream.setPath(request.getRequestURI());
            }
            if (downstream.getTimestamp() == null) {
                downstream.setTimestamp(java.time.LocalDateTime.now());
            }
            return ResponseEntity.status(ex.getStatusCode()).body(downstream);
        }
        ErrorResponse body = ErrorResponse.of(ex.getStatusCode().value(), ex.getMessage(), request.getRequestURI());
        body.setService(ex.getServiceName());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(ErrorResponse.validation(request.getRequestURI(), fieldErrors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(400, "JSON inválido", request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(400, "Parámetro inválido: " + ex.getName(), request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(500, "Error interno del BFF", request.getRequestURI()));
    }
}
