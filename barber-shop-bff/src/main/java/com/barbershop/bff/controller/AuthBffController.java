package com.barbershop.bff.controller;

import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.user.LoginRequestDto;
import com.barbershop.bff.dto.user.LoginResponseDto;
import com.barbershop.bff.dto.user.UserDto;
import com.barbershop.bff.dto.user.UserRequestDto;
import com.barbershop.bff.service.AuthBffService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BFF: autenticación expuesta al frontend (delega en user-service).
 */
@RestController
@RequestMapping("/bff/auth")
public class AuthBffController {

    private final AuthBffService authBffService;

    public AuthBffController(AuthBffService authBffService) {
        this.authBffService = authBffService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody UserRequestDto request) {
        return authBffService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        return authBffService.login(request);
    }
}
