package com.barbershop.userservice.service;

import com.barbershop.userservice.dto.LoginRequestDto;
import com.barbershop.userservice.dto.LoginResponseDto;
import com.barbershop.userservice.dto.UserDto;
import com.barbershop.userservice.dto.UserRequestDto;
import com.barbershop.userservice.exception.EmailAlreadyExistsException;
import com.barbershop.userservice.exception.InvalidCredentialsException;
import com.barbershop.userservice.exception.UserNotFoundException;
import com.barbershop.userservice.mapper.UserMapper;
import com.barbershop.userservice.model.User;
import com.barbershop.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Archivo: user/src/test/java/com/barbershop/userservice/service/UserServiceTest.java
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserMapper userMapper;
    private UserServiceImpl userService;

    private User sampleUser;
    private UserRequestDto registerRequest;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
        userService = new UserServiceImpl(userRepository, userMapper);

        sampleUser = User.builder()
                .id(1L)
                .name("Juan Pérez")
                .email("juan@email.com")
                .password("cliente123")
                .role("CLIENT")
                .build();

        registerRequest = UserRequestDto.builder()
                .name("Carlos Ruiz")
                .email("carlos@email.com")
                .password("miPassword123")
                .role("CLIENT")
                .build();
    }

    @Test
    @DisplayName("register - debe registrar usuario y no devolver password")
    void register_shouldCreateUserWithoutPasswordInDto() {
        when(userRepository.existsByEmailIgnoreCase("carlos@email.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(2L);
            return u;
        });

        UserDto result = userService.register(registerRequest);

        assertNotNull(result);
        assertEquals("carlos@email.com", result.getEmail());
        assertEquals("CLIENT", result.getRole());
        // UserDto no expone password (solo id, name, email, role)
        assertThrows(NoSuchFieldException.class,
                () -> UserDto.class.getDeclaredField("password"));
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("register - debe lanzar excepción si email existe")
    void register_whenEmailExists_shouldThrow() {
        when(userRepository.existsByEmailIgnoreCase("carlos@email.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.register(registerRequest));
    }

    @Test
    @DisplayName("login - credenciales correctas")
    void login_withValidCredentials_shouldReturnLoginResponse() {
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email("juan@email.com")
                .password("cliente123")
                .build();

        when(userRepository.findByEmailIgnoreCase("juan@email.com")).thenReturn(Optional.of(sampleUser));

        LoginResponseDto result = userService.login(loginRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("juan@email.com", result.getEmail());
        assertEquals("CLIENT", result.getRole());
    }

    @Test
    @DisplayName("login - credenciales incorrectas por password")
    void login_withWrongPassword_shouldThrow() {
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email("juan@email.com")
                .password("wrongPassword123")
                .build();

        when(userRepository.findByEmailIgnoreCase("juan@email.com")).thenReturn(Optional.of(sampleUser));

        assertThrows(InvalidCredentialsException.class, () -> userService.login(loginRequest));
    }

    @Test
    @DisplayName("login - credenciales incorrectas por email")
    void login_withUnknownEmail_shouldThrow() {
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email("noexiste@email.com")
                .password("cliente123")
                .build();

        when(userRepository.findByEmailIgnoreCase("noexiste@email.com")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> userService.login(loginRequest));
    }

    @Test
    @DisplayName("findById - debe encontrar usuario sin password en DTO")
    void findById_shouldReturnUserWithoutPassword() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        UserDto result = userService.findById(1L);

        assertNotNull(result);
        assertEquals("Juan Pérez", result.getName());
        assertEquals("juan@email.com", result.getEmail());
    }

    @Test
    @DisplayName("findById - debe lanzar excepción si no existe")
    void findById_whenNotExists_shouldThrow() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(99L));
    }

    @Test
    @DisplayName("update - debe actualizar usuario")
    void update_shouldUpdateUser() {
        UserRequestDto updateRequest = UserRequestDto.builder()
                .name("Juan Actualizado")
                .email("juan@email.com")
                .password("nuevaClave123")
                .role("CLIENT")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userRepository.findByEmailIgnoreCase("juan@email.com")).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        UserDto result = userService.update(1L, updateRequest);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("deleteById - debe eliminar usuario")
    void deleteById_shouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteById(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteById - debe lanzar excepción si no existe")
    void deleteById_whenNotExists_shouldThrow() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteById(99L));
    }
}
