package com.barbershop.userservice.controller;

import com.barbershop.userservice.dto.ApiResponse;
import com.barbershop.userservice.dto.LoginRequestDto;
import com.barbershop.userservice.dto.LoginResponseDto;
import com.barbershop.userservice.dto.UserDto;
import com.barbershop.userservice.dto.UserRequestDto;
import com.barbershop.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Capa REST: endpoints de usuarios y autenticación básica.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** GET /api/users */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.ok(userService.findAll()));
    }

    /** GET /api/users/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(userService.findById(id)));
    }

    /** POST /api/users/register */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody UserRequestDto request) {
        UserDto created = userService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Usuario registrado correctamente", created));
    }

    /** POST /api/users/login */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto session = userService.login(request);
        return ResponseEntity.ok(ApiResponse.ok("Inicio de sesión exitoso", session));
    }

    /** PUT /api/users/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto request) {
        UserDto updated = userService.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Usuario actualizado correctamente", updated));
    }

    /** DELETE /api/users/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Usuario eliminado correctamente"));
    }
}
