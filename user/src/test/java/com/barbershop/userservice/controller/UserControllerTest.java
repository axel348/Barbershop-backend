package com.barbershop.userservice.controller;

import com.barbershop.userservice.dto.LoginRequestDto;
import com.barbershop.userservice.dto.LoginResponseDto;
import com.barbershop.userservice.dto.UserDto;
import com.barbershop.userservice.dto.UserRequestDto;
import com.barbershop.userservice.exception.GlobalExceptionHandler;
import com.barbershop.userservice.exception.InvalidCredentialsException;
import com.barbershop.userservice.exception.UserNotFoundException;
import com.barbershop.userservice.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Archivo: user/src/test/java/com/barbershop/userservice/controller/UserControllerTest.java
 */
@WebMvcTest(UserController.class)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

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

    private static final String LOGIN_WRONG_JSON = """
            {
              "email": "juan@email.com",
              "password": "wrongPassword123"
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

    private final UserDto sampleUserDto = UserDto.builder()
            .id(1L)
            .name("Juan Pérez")
            .email("juan@email.com")
            .role("CLIENT")
            .build();

    @Test
    @DisplayName("POST /api/users/register - registro sin password en respuesta")
    void register_shouldReturn201WithoutPassword() throws Exception {
        UserDto created = UserDto.builder()
                .id(2L)
                .name("Carlos Ruiz")
                .email("carlos@email.com")
                .role("CLIENT")
                .build();

        when(userService.register(any(UserRequestDto.class))).thenReturn(created);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REGISTER_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value("carlos@email.com"))
                .andExpect(jsonPath("$.data.password").doesNotExist())
                .andExpect(jsonPath("$.data.id").value(2));

        verify(userService).register(any(UserRequestDto.class));
    }

    @Test
    @DisplayName("POST /api/users/login - login correcto")
    void login_withValidCredentials_shouldReturn200() throws Exception {
        LoginResponseDto loginResponse = LoginResponseDto.builder()
                .id(1L)
                .name("Juan Pérez")
                .email("juan@email.com")
                .role("CLIENT")
                .build();

        when(userService.login(any(LoginRequestDto.class))).thenReturn(loginResponse);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LOGIN_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Inicio de sesión exitoso"))
                .andExpect(jsonPath("$.data.email").value("juan@email.com"))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    @Test
    @DisplayName("POST /api/users/login - login incorrecto retorna 401")
    void login_withInvalidCredentials_shouldReturn401() throws Exception {
        when(userService.login(any(LoginRequestDto.class)))
                .thenThrow(new InvalidCredentialsException());

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LOGIN_WRONG_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email o contraseña incorrectos"));
    }

    @Test
    @DisplayName("GET /api/users/{id} - buscar usuario sin password")
    void getUserById_shouldReturn200WithoutPassword() throws Exception {
        when(userService.findById(1L)).thenReturn(sampleUserDto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    @Test
    @DisplayName("GET /api/users/{id} - 404 si no existe")
    void getUserById_whenNotFound_shouldReturn404() throws Exception {
        when(userService.findById(99L)).thenThrow(new UserNotFoundException(99L));

        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/users/{id} - actualizar usuario")
    void updateUser_shouldReturn200() throws Exception {
        UserDto updated = UserDto.builder()
                .id(1L)
                .name("Juan Actualizado")
                .email("juan@email.com")
                .role("CLIENT")
                .build();

        when(userService.update(eq(1L), any(UserRequestDto.class))).thenReturn(updated);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_USER_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Juan Actualizado"))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    @Test
    @DisplayName("DELETE /api/users/{id} - eliminar usuario")
    void deleteUser_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Usuario eliminado correctamente"));

        verify(userService).deleteById(1L);
    }
}
