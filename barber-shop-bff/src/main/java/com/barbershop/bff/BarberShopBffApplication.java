package com.barbershop.bff;

import com.barbershop.bff.config.CartServiceProperties;
import com.barbershop.bff.config.ProductServiceProperties;
import com.barbershop.bff.config.UserServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        ProductServiceProperties.class,
        UserServiceProperties.class,
        CartServiceProperties.class
})
public class BarberShopBffApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarberShopBffApplication.class, args);
    }
}
