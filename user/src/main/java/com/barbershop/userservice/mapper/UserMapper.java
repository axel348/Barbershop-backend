package com.barbershop.userservice.mapper;

import com.barbershop.userservice.dto.LoginResponseDto;
import com.barbershop.userservice.dto.UserDto;
import com.barbershop.userservice.dto.UserRequestDto;
import com.barbershop.userservice.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Convierte entre entidad User y DTOs (sin exponer password en salidas públicas).
 */
@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public LoginResponseDto toLoginResponseDto(User user) {
        return LoginResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public List<UserDto> toDtoList(List<User> users) {
        return users.stream().map(this::toDto).toList();
    }

    public User toEntity(UserRequestDto request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail().trim().toLowerCase())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }

    public void updateEntity(User user, UserRequestDto request) {
        user.setName(request.getName());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
    }
}
