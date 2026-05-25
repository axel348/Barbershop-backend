package com.barbershop.bff.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * product.service.url=http://localhost:8081/api/products
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "product.service")
public class ProductServiceProperties {

    private String url;
}
