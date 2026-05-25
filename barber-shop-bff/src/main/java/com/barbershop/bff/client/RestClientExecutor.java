package com.barbershop.bff.client;

import com.barbershop.bff.exception.ErrorResponse;
import com.barbershop.bff.exception.MicroserviceClientException;
import com.barbershop.bff.exception.MicroserviceUnavailableException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.util.function.Supplier;

/**
 * Ejecuta llamadas RestTemplate con manejo uniforme de errores.
 */
@Component
public class RestClientExecutor {

    public <T> ResponseEntity<T> execute(String serviceName, Supplier<ResponseEntity<T>> call) {
        try {
            ResponseEntity<T> response = call.get();
            if (response == null) {
                throw new MicroserviceUnavailableException(serviceName,
                        new RestClientException("Respuesta nula del microservicio"));
            }
            return response;
        } catch (ResourceAccessException ex) {
            throw new MicroserviceUnavailableException(serviceName, ex);
        } catch (HttpStatusCodeException ex) {
            ErrorResponse errorBody = parseErrorBody(ex);
            throw new MicroserviceClientException(serviceName, ex.getStatusCode(), errorBody);
        }
    }

    private ErrorResponse parseErrorBody(HttpStatusCodeException ex) {
        try {
            return ex.getResponseBodyAs(ErrorResponse.class);
        } catch (Exception e) {
            String raw = ex.getResponseBodyAsString();
            if (raw == null || raw.isBlank()) {
                return null;
            }
            return ErrorResponse.builder()
                    .success(false)
                    .message(raw)
                    .status(ex.getStatusCode().value())
                    .build();
        }
    }
}
