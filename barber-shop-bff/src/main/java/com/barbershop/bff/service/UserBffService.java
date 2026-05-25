package com.barbershop.bff.service;

import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.user.UserDto;
import com.barbershop.bff.dto.user.UserRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserBffService {

    ResponseEntity<ApiResponse<List<UserDto>>> findAll();

    ResponseEntity<ApiResponse<UserDto>> findById(Long id);

    ResponseEntity<ApiResponse<UserDto>> update(Long id, UserRequestDto request);

    ResponseEntity<ApiResponse<Void>> delete(Long id);
}
