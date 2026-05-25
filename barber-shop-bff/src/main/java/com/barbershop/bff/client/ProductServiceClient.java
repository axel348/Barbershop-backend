package com.barbershop.bff.client;

import com.barbershop.bff.config.ProductServiceProperties;
import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.product.ProductRequestDto;
import com.barbershop.bff.dto.product.ProductResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Client/Adapter: comunicación HTTP con product-service.
 */
@Component
public class ProductServiceClient {

    private static final String SERVICE_NAME = "product-service";

    private final RestTemplate restTemplate;
    private final RestClientExecutor restClientExecutor;
    private final String baseUrl;

    public ProductServiceClient(
            RestTemplate restTemplate,
            RestClientExecutor restClientExecutor,
            ProductServiceProperties properties) {
        this.restTemplate = restTemplate;
        this.restClientExecutor = restClientExecutor;
        this.baseUrl = properties.getUrl();
    }

    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> findAll() {
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<List<ProductResponseDto>>>() {}));
    }

    public ResponseEntity<ApiResponse<ProductResponseDto>> findById(Long id) {
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl + "/" + id,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<ProductResponseDto>>() {}));
    }

    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> findByCategory(String category) {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/category/{category}")
                .buildAndExpand(category)
                .toUriString();
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<List<ProductResponseDto>>>() {}));
    }

    public ResponseEntity<ApiResponse<ProductResponseDto>> create(ProductRequestDto request) {
        HttpEntity<ProductRequestDto> entity = new HttpEntity<>(request);
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl,
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<ProductResponseDto>>() {}));
    }

    public ResponseEntity<ApiResponse<ProductResponseDto>> update(Long id, ProductRequestDto request) {
        HttpEntity<ProductRequestDto> entity = new HttpEntity<>(request);
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl + "/" + id,
                        HttpMethod.PUT,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<ProductResponseDto>>() {}));
    }

    public ResponseEntity<ApiResponse<Void>> delete(Long id) {
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl + "/" + id,
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<ApiResponse<Void>>() {}));
    }
}
