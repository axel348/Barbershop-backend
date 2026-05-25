package com.barbershop.bff.config;

import org.springframework.context.annotation.Configuration;

/**
 * Preparación JWT en el BFF (capa recomendada para validar tokens del frontend).
 *
 * <p><b>Flujo objetivo con JWT:</b></p>
 * <ol>
 *   <li>Frontend hace login en {@code POST /bff/auth/login} → user-service devuelve JWT.</li>
 *   <li>Frontend envía {@code Authorization: Bearer &lt;token&gt;} en cada llamada al BFF.</li>
 *   <li>BFF: {@code JwtAuthenticationFilter} valida token (firma, expiración, rol).</li>
 *   <li>Si es válido, reenvía la petición a product-service / user-service.</li>
 *   <li>Rutas públicas: login, register, GET productos (catálogo).</li>
 * </ol>
 *
 * <p>Ventaja: los microservicios internos no se exponen a Internet; solo el BFF valida identidad.</p>
 */
@Configuration
public class JwtSecurityConfig {
    // Ver JwtAuthenticationFilter (futuro) en paquete security/
}
