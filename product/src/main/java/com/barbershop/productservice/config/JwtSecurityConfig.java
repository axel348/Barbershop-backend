package com.barbershop.productservice.config;

import org.springframework.context.annotation.Configuration;

/**
 * Preparación para seguridad JWT (no activa en esta versión académica).
 *
 * <p><b>Integración futura en product-service (si aplica):</b></p>
 * <ol>
 *   <li>El BFF validaría el token JWT del cliente antes de reenviar peticiones.</li>
 *   <li>Opcional: este servicio podría validar roles (ADMIN) para POST/PUT/DELETE.</li>
 *   <li>Agregar dependencia: {@code spring-boot-starter-oauth2-resource-server} o {@code jjwt}.</li>
 *   <li>Registrar filtro {@code JwtAuthenticationFilter} en la cadena de Spring Security.</li>
 *   <li>Proteger endpoints de escritura con {@code @PreAuthorize("hasRole('ADMIN')")}.</li>
 * </ol>
 *
 * <p>Actualmente la seguridad de productos se delega al BFF y al user-service (autenticación).</p>
 */
@Configuration
public class JwtSecurityConfig {
    // Punto de extensión documentado para defensa oral y evolución a producción.
}
