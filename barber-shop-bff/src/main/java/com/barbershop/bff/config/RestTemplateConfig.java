package com.barbershop.bff.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración del cliente HTTP RestTemplate.
 */
@Configuration
@EnableConfigurationProperties({ProductServiceProperties.class, UserServiceProperties.class, CartServiceProperties.class})
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(
            @Value("${bff.rest.connect-timeout:5000}") int connectTimeout,
            @Value("${bff.rest.read-timeout:10000}") int readTimeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return new RestTemplate(factory);
    }
}
