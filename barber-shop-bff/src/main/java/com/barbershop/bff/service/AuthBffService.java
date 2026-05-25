package com.barbershop.bff.service;

import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.user.LoginRequestDto;
import com.barbershop.bff.dto.user.LoginResponseDto;
import com.barbershop.bff.dto.user.UserDto;
import com.barbershop.bff.dto.user.UserRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthBffService {

    ResponseEntity<ApiResponse<UserDto>> register(UserRequestDto request);

    ResponseEntity<ApiResponse<LoginResponseDto>> login(LoginRequestDto request);
}
