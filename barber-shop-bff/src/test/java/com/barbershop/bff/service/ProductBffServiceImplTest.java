package com.barbershop.bff.service;

import com.barbershop.bff.client.ProductServiceClient;
import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.product.ProductRequestDto;
import com.barbershop.bff.dto.product.ProductResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductBffServiceImplTest {

    @Mock
    private ProductServiceClient productServiceClient;

    @InjectMocks
    private ProductBffServiceImpl productBffService;

    @Test
    @DisplayName("findAll - delega en ProductServiceClient")
    void findAll_delegatesToClient() {
        ApiResponse<List<ProductResponseDto>> body = ApiResponse.<List<ProductResponseDto>>builder()
                .success(true)
                .data(List.of())
                .build();
        when(productServiceClient.findAll()).thenReturn(ResponseEntity.ok(body));

        ResponseEntity<ApiResponse<List<ProductResponseDto>>> result = productBffService.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(productServiceClient).findAll();
    }

    @Test
    @DisplayName("findById - delega en ProductServiceClient")
    void findById_delegatesToClient() {
        when(productServiceClient.findById(1L)).thenReturn(ResponseEntity.ok(ApiResponse.<ProductResponseDto>builder().success(true).build()));

        productBffService.findById(1L);

        verify(productServiceClient).findById(1L);
    }

    @Test
    @DisplayName("create - delega en ProductServiceClient")
    void create_delegatesToClient() {
        ProductRequestDto request = ProductRequestDto.builder().name("Test").build();
        when(productServiceClient.create(request)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        productBffService.create(request);

        verify(productServiceClient).create(request);
    }
}
