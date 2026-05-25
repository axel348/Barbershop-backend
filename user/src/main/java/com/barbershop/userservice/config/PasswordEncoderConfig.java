package com.barbershop.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Codificación de contraseñas (preparado para producción).
 *
 * <p>En desarrollo, {@code UserServiceImpl} puede seguir comparando texto plano.
 * Para activar BCrypt: en registro usar {@code passwordEncoder.encode(password)}
 * y en login {@code passwordEncoder.matches(raw, encoded)}.</p>
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
