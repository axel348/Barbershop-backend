package com.barbershop.bff.service;

import com.barbershop.bff.client.UserServiceClient;
import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.user.LoginRequestDto;
import com.barbershop.bff.dto.user.LoginResponseDto;
import com.barbershop.bff.dto.user.UserDto;
import com.barbershop.bff.dto.user.UserRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthBffServiceImplTest {

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private AuthBffServiceImpl authBffService;

    @Test
    @DisplayName("register - delega en UserServiceClient")
    void register_delegatesToClient() {
        UserRequestDto request = UserRequestDto.builder().email("a@b.com").build();
        when(userServiceClient.register(request)).thenReturn(ResponseEntity.ok(ApiResponse.<UserDto>builder().success(true).build()));

        authBffService.register(request);

        verify(userServiceClient).register(request);
    }

    @Test
    @DisplayName("login - delega en UserServiceClient")
    void login_delegatesToClient() {
        LoginRequestDto request = LoginRequestDto.builder().email("a@b.com").password("123456").build();
        when(userServiceClient.login(request)).thenReturn(ResponseEntity.ok(ApiResponse.<LoginResponseDto>builder().success(true).build()));

        authBffService.login(request);

        verify(userServiceClient).login(request);
    }
}
