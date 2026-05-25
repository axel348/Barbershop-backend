package com.barbershop.bff.client;

import com.barbershop.bff.config.UserServiceProperties;
import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.user.LoginRequestDto;
import com.barbershop.bff.dto.user.LoginResponseDto;
import com.barbershop.bff.dto.user.UserDto;
import com.barbershop.bff.dto.user.UserRequestDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Client/Adapter: comunicación HTTP con user-service.
 */
@Component
public class UserServiceClient {

    private static final String SERVICE_NAME = "user-service";

    private final RestTemplate restTemplate;
    private final RestClientExecutor restClientExecutor;
    private final String baseUrl;

    public UserServiceClient(
            RestTemplate restTemplate,
            RestClientExecutor restClientExecutor,
            UserServiceProperties properties) {
        this.restTemplate = restTemplate;
        this.restClientExecutor = restClientExecutor;
        this.baseUrl = properties.getUrl();
    }

    public ResponseEntity<ApiResponse<List<UserDto>>> findAll() {
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<List<UserDto>>>() {}));
    }

    public ResponseEntity<ApiResponse<UserDto>> findById(Long id) {
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl + "/" + id,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<UserDto>>() {}));
    }

    public ResponseEntity<ApiResponse<UserDto>> register(UserRequestDto request) {
        HttpEntity<UserRequestDto> entity = new HttpEntity<>(request);
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl + "/register",
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<UserDto>>() {}));
    }

    public ResponseEntity<ApiResponse<LoginResponseDto>> login(LoginRequestDto request) {
        HttpEntity<LoginRequestDto> entity = new HttpEntity<>(request);
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl + "/login",
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<LoginResponseDto>>() {}));
    }

    public ResponseEntity<ApiResponse<UserDto>> update(Long id, UserRequestDto request) {
        HttpEntity<UserRequestDto> entity = new HttpEntity<>(request);
        return restClientExecutor.execute(SERVICE_NAME, () ->
                restTemplate.exchange(
                        baseUrl + "/" + id,
                        HttpMethod.PUT,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<UserDto>>() {}));
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
