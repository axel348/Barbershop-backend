package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del microservicio ${artifactId}.
 */
@SpringBootApplication
public class ${serviceName}ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(${serviceName}ServiceApplication.class, args);
    }
}
