package com.barbershop.userservice.service;

import com.barbershop.userservice.dto.LoginRequestDto;
import com.barbershop.userservice.dto.LoginResponseDto;
import com.barbershop.userservice.dto.UserDto;
import com.barbershop.userservice.dto.UserRequestDto;

import java.util.List;

/**
 * Contrato de la capa de negocio (Service Layer Pattern).
 */
public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto register(UserRequestDto request);

    LoginResponseDto login(LoginRequestDto request);

    UserDto update(Long id, UserRequestDto request);

    void deleteById(Long id);
}
