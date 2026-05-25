package com.barbershop.bff.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * user.service.url=http://localhost:8082/api/users
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "user.service")
public class UserServiceProperties {

    private String url;
}
