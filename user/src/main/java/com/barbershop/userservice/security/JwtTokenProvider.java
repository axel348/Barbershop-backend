package com.barbershop.userservice.security;

import org.springframework.stereotype.Component;

/**
 * Stub para generación y validación de tokens JWT.
 *
 * <p><b>Ejemplo de implementación futura con jjwt:</b></p>
 * <pre>
 * public String generateToken(User user) {
 *     return Jwts.builder()
 *         .subject(user.getEmail())
 *         .claim("role", user.getRole())
 *         .claim("userId", user.getId())
 *         .expiration(new Date(System.currentTimeMillis() + expirationMs))
 *         .signWith(secretKey)
 *         .compact();
 * }
 * </pre>
 */
@Component
public class JwtTokenProvider {

    /**
     * Generará el JWT tras login exitoso (pendiente).
     */
    public String generateToken(Long userId, String email, String role) {
        // TODO: implementar con secret desde application.properties (jwt.secret)
        return null;
    }

    /**
     * Validará el Bearer token en requests protegidos (pendiente).
     */
    public boolean validateToken(String token) {
        // TODO: parsear y verificar firma + expiración
        return false;
    }
}
