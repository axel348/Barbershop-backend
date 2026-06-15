package com.barbershop.bff.client;

import com.barbershop.bff.exception.ErrorResponse;
import com.barbershop.bff.exception.MicroserviceClientException;
import com.barbershop.bff.exception.MicroserviceUnavailableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestClientExecutorTest {

    private final RestClientExecutor executor = new RestClientExecutor();

    @Test
    @DisplayName("execute - retorna respuesta exitosa")
    void execute_success() {
        ResponseEntity<String> expected = ResponseEntity.ok("ok");

        ResponseEntity<String> result = executor.execute("product-service", () -> expected);

        assertEquals("ok", result.getBody());
    }

    @Test
    @DisplayName("execute - lanza excepción si respuesta es nula")
    void execute_nullResponse() {
        assertThrows(MicroserviceUnavailableException.class,
                () -> executor.execute("user-service", () -> null));
    }

    @Test
    @DisplayName("execute - microservicio no disponible")
    void execute_resourceAccessException() {
        assertThrows(MicroserviceUnavailableException.class,
                () -> executor.execute("product-service",
                        () -> { throw new ResourceAccessException("Connection refused"); }));
    }

    @Test
    @DisplayName("execute - error HTTP del microservicio")
    void execute_httpStatusCodeException() {
        HttpClientErrorException httpError = HttpClientErrorException.create(
                HttpStatus.NOT_FOUND,
                "Not Found",
                null,
                "{\"success\":false,\"message\":\"No encontrado\",\"status\":404}".getBytes(),
                null);

        MicroserviceClientException ex = assertThrows(MicroserviceClientException.class,
                () -> executor.execute("product-service", () -> { throw httpError; }));

        assertEquals("product-service", ex.getServiceName());
        assertEquals(404, ex.getStatusCode().value());
    }

    @Test
    @DisplayName("execute - error HTTP sin cuerpo JSON parseable")
    void execute_httpErrorWithoutJsonBody() {
        HttpClientErrorException httpError = HttpClientErrorException.create(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                null,
                "error plano".getBytes(),
                null);

        MicroserviceClientException ex = assertThrows(MicroserviceClientException.class,
                () -> executor.execute("user-service", () -> { throw httpError; }));

        ErrorResponse body = ex.getErrorBody();
        assertNotNull(body);
        assertEquals("error plano", body.getMessage());
    }
}
