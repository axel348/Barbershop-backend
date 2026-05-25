package com.barbershop.bff.service;

import com.barbershop.bff.client.UserServiceClient;
import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.user.LoginRequestDto;
import com.barbershop.bff.dto.user.LoginResponseDto;
import com.barbershop.bff.dto.user.UserDto;
import com.barbershop.bff.dto.user.UserRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthBffServiceImpl implements AuthBffService {

    private final UserServiceClient userServiceClient;

    public AuthBffServiceImpl(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public ResponseEntity<ApiResponse<UserDto>> register(UserRequestDto request) {
        return userServiceClient.register(request);
    }

    @Override
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(LoginRequestDto request) {
        return userServiceClient.login(request);
    }
}
