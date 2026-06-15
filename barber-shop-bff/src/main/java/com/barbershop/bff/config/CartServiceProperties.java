package com.barbershop.bff.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * cart.service.url=http://localhost:8083/api/cart
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "cart.service")
public class CartServiceProperties {

    private String url;
}
