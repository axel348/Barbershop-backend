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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Lógica de negocio CRUD y autenticación básica por email/password.
 *
 * <p><b>Seguridad (evolución):</b></p>
 * <ul>
 *   <li>Las respuestas usan {@code UserDto} / {@code LoginResponseDto} sin campo password.</li>
 *   <li>Producción: inyectar {@code PasswordEncoder} y usar {@code encode()} / {@code matches()}.</li>
 *   <li>Tras login: generar JWT con {@code JwtTokenProvider} (ver paquete security).</li>
 * </ul>
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> findAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto register(UserRequestDto request) {
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailAlreadyExistsException(email);
        }
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmailIgnoreCase(request.getEmail().trim())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return userMapper.toLoginResponseDto(user);
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        String email = request.getEmail().trim().toLowerCase();
        userRepository.findByEmailIgnoreCase(email).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new EmailAlreadyExistsException(email);
            }
        });

        userMapper.updateEntity(user, request);
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
