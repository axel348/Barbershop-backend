package com.barbershop.bff.controller;

import com.barbershop.bff.dto.ApiResponse;
import com.barbershop.bff.dto.user.LoginRequestDto;
import com.barbershop.bff.dto.user.LoginResponseDto;
import com.barbershop.bff.dto.user.UserDto;
import com.barbershop.bff.dto.user.UserRequestDto;
import com.barbershop.bff.service.AuthBffService;
import com.barbershop.bff.service.UserBffService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Archivo: barber-shop-bff/src/test/java/com/barbershop/bff/controller/BffUserControllerTest.java
 * Incluye pruebas de UserBffController y AuthBffController (simulando microservicio user-service).
 */
@WebMvcTest({UserBffController.class, AuthBffController.class})
class BffUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserBffService userBffService;

    @MockitoBean
    private AuthBffService authBffService;

    private static final String REGISTER_JSON = """
            {
              "name": "Carlos Ruiz",
              "email": "carlos@email.com",
              "password": "miPassword123",
              "role": "CLIENT"
            }
            """;

    private static final String LOGIN_JSON = """
            {
              "email": "juan@email.com",
              "password": "cliente123"
            }
            """;

    private static final String UPDATE_USER_JSON = """
            {
              "name": "Juan Actualizado",
              "email": "juan@email.com",
              "password": "nuevaClave123",
              "role": "CLIENT"
            }
            """;

    private final UserDto sampleUser = UserDto.builder()
            .id(1L)
            .name("Juan Pérez")
            .email("juan@email.com")
            .role("CLIENT")
            .build();

    @Test
    @DisplayName("GET /bff/users - consume listado de usuarios")
    void getAllUsers_shouldReturn200() throws Exception {
        ApiResponse<List<UserDto>> response = ApiResponse.<List<UserDto>>builder()
                .success(true)
                .data(List.of(sampleUser))
                .timestamp(LocalDateTime.now())
                .build();

        when(userBffService.findAll()).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(get("/bff/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].email").value("juan@email.com"))
                .andExpect(jsonPath("$.data[0].password").doesNotExist());

        verify(userBffService).findAll();
    }

    @Test
    @DisplayName("GET /bff/users/{id} - consume usuario por ID")
    void getUserById_shouldReturn200() throws Exception {
        ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                .success(true)
                .data(sampleUser)
                .timestamp(LocalDateTime.now())
                .build();

        when(userBffService.findById(1L)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(get("/bff/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    @Test
    @DisplayName("POST /bff/auth/register - consume registro de user-service")
    void register_shouldReturn201() throws Exception {
        UserDto created = UserDto.builder()
                .id(2L)
                .name("Carlos Ruiz")
                .email("carlos@email.com")
                .role("CLIENT")
                .build();

        ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                .success(true)
                .message("Usuario registrado correctamente")
                .data(created)
                .timestamp(LocalDateTime.now())
                .build();

        when(authBffService.register(any(UserRequestDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));

        mockMvc.perform(post("/bff/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REGISTER_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value("carlos@email.com"))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    @Test
    @DisplayName("POST /bff/auth/login - consume login de user-service")
    void login_shouldReturn200() throws Exception {
        LoginResponseDto loginData = LoginResponseDto.builder()
                .id(1L)
                .name("Juan Pérez")
                .email("juan@email.com")
                .role("CLIENT")
                .build();

        ApiResponse<LoginResponseDto> response = ApiResponse.<LoginResponseDto>builder()
                .success(true)
                .message("Inicio de sesión exitoso")
                .data(loginData)
                .timestamp(LocalDateTime.now())
                .build();

        when(authBffService.login(any(LoginRequestDto.class)))
                .thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(post("/bff/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LOGIN_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.role").value("CLIENT"))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    @Test
    @DisplayName("PUT /bff/users/{id} - consume actualización de usuario")
    void updateUser_shouldReturn200() throws Exception {
        UserDto updated = UserDto.builder()
                .id(1L)
                .name("Juan Actualizado")
                .email("juan@email.com")
                .role("CLIENT")
                .build();

        ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                .success(true)
                .data(updated)
                .timestamp(LocalDateTime.now())
                .build();

        when(userBffService.update(eq(1L), any(UserRequestDto.class)))
                .thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(put("/bff/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_USER_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Juan Actualizado"));
    }

    @Test
    @DisplayName("DELETE /bff/users/{id} - consume eliminación de usuario")
    void deleteUser_shouldReturn200() throws Exception {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Usuario eliminado correctamente")
                .timestamp(LocalDateTime.now())
                .build();

        when(userBffService.delete(1L)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(delete("/bff/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Usuario eliminado correctamente"));

        verify(userBffService).delete(1L);
    }
}
