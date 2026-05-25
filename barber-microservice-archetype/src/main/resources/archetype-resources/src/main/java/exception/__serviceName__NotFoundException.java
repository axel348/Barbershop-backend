package ${package}.exception;

public class ${serviceName}NotFoundException extends RuntimeException {

    public ${serviceName}NotFoundException(Long id) {
        super("${serviceName} no encontrado con id: " + id);
    }
}
