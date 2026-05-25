package com.barbershop.bff.service;

import com.barbershop.bff.client.UserServiceClient;
import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.user.UserDto;
import com.barbershop.bff.dto.user.UserRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBffServiceImpl implements UserBffService {

    private final UserServiceClient userServiceClient;

    public UserBffServiceImpl(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public ResponseEntity<ApiResponse<List<UserDto>>> findAll() {
        return userServiceClient.findAll();
    }

    @Override
    public ResponseEntity<ApiResponse<UserDto>> findById(Long id) {
        return userServiceClient.findById(id);
    }

    @Override
    public ResponseEntity<ApiResponse<UserDto>> update(Long id, UserRequestDto request) {
        return userServiceClient.update(id, request);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(Long id) {
        return userServiceClient.delete(id);
    }
}
