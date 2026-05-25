package com.barbershop.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * BFF: punto único de entrada para el frontend de la barbería.
 */
@SpringBootApplication
public class BarberShopBffApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarberShopBffApplication.class, args);
    }
}
