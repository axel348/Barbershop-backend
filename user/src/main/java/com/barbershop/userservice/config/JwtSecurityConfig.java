package com.barbershop.userservice.config;

import org.springframework.context.annotation.Configuration;

/**
 * Preparación para autenticación JWT.
 *
 * <p><b>Pasos para integrar JWT en user-service:</b></p>
 * <ol>
 *   <li>Agregar {@code spring-boot-starter-security} y librería JWT (jjwt u OAuth2).</li>
 *   <li>En {@code UserServiceImpl.login()}: tras validar credenciales, generar token firmado
 *       con claims (id, email, role, exp).</li>
 *   <li>Crear {@code JwtTokenProvider} con métodos {@code generateToken()} y {@code validateToken()}.</li>
 *   <li>Registrar {@code SecurityFilterChain}: rutas {@code /api/users/login} y
 *       {@code /api/users/register} públicas; resto autenticadas.</li>
 *   <li>Usar {@code PasswordEncoder} (BCrypt) — ver {@link PasswordEncoderConfig}.</li>
 *   <li>Devolver en login: {@code { "token": "...", "user": { ... } }}.</li>
 * </ol>
 *
 * <p>El BFF recibiría el token y lo enviaría en header {@code Authorization: Bearer &lt;token&gt;}.</p>
 */
@Configuration
public class JwtSecurityConfig {
    // Extensión planificada — ver DEFENSA_ORAL.md
}
