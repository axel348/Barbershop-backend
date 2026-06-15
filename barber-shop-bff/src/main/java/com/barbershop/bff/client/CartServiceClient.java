package com.barbershop.bff.client;

import com.barbershop.bff.config.CartServiceProperties;
import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.cart.CartItemRequestDto;
import com.barbershop.bff.dto.cart.CartItemResponseDto;
import com.barbershop.bff.dto.cart.CartItemUpdateRequestDto;
import com.barbershop.bff.dto.cart.CartResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CartServiceClient {

    private static final String SERVICE_NAME = "cart-service";

    private final RestTemplate restTemplate;
    private final RestClientExecutor restClientExecutor;
    private final String baseUrl;

    public CartServiceClient(
            RestTemplate restTemplate,
            RestClientExecutor restClientExecutor,
            CartServiceProperties properties) {
        this.restTemplate = restTemplate;
        this.restClientExecutor = restClientExecutor;
        this.baseUrl = properties.getUrl();
    }

    public ResponseEntity<ApiResponse<CartResponseDto>> getCart() {
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<CartResponseDto>>() {}));
    }

    public ResponseEntity<ApiResponse<CartItemResponseDto>> addItem(CartItemRequestDto request) {
        HttpEntity<CartItemRequestDto> entity = new HttpEntity<>(request);
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl + "/items",
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<CartItemResponseDto>>() {}));
    }

    public ResponseEntity<ApiResponse<CartItemResponseDto>> updateItem(
            Long id,
            CartItemUpdateRequestDto request) {
        HttpEntity<CartItemUpdateRequestDto> entity = new HttpEntity<>(request);
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl + "/items/" + id,
                        HttpMethod.PUT,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<CartItemResponseDto>>() {}));
    }

    public ResponseEntity<ApiResponse<Void>> removeItem(Long id) {
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl + "/items/" + id,
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<ApiResponse<Void>>() {}));
    }

    public ResponseEntity<ApiResponse<Void>> clearCart() {
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl + "/clear",
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<ApiResponse<Void>>() {}));
    }
}
