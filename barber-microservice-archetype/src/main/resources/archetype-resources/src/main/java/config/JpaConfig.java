package ${package}.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuración JPA y transacciones.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "${package}.repository")
public class JpaConfig {
}
