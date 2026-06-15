package com.barbershop.bff.service;

import com.barbershop.bff.client.UserServiceClient;
import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.user.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserBffServiceImplTest {

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private UserBffServiceImpl userBffService;

    @Test
    @DisplayName("findAll - delega en UserServiceClient")
    void findAll_delegatesToClient() {
        when(userServiceClient.findAll()).thenReturn(ResponseEntity.ok(ApiResponse.<List<UserDto>>builder().success(true).build()));

        userBffService.findAll();

        verify(userServiceClient).findAll();
    }

    @Test
    @DisplayName("findById - delega en UserServiceClient")
    void findById_delegatesToClient() {
        when(userServiceClient.findById(2L)).thenReturn(ResponseEntity.ok(ApiResponse.<UserDto>builder().success(true).build()));

        userBffService.findById(2L);

        verify(userServiceClient).findById(2L);
    }
}
