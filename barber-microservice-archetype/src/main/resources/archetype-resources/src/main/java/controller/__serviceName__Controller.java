package ${package}.controller;

import ${package}.dto.ApiResponse;
import ${package}.dto.${serviceName}RequestDto;
import ${package}.dto.${serviceName}ResponseDto;
import ${package}.service.${serviceName}Service;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/${apiPath}")
public class ${serviceName}Controller {

    private final ${serviceName}Service service;

    public ${serviceName}Controller(${serviceName}Service service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<${serviceName}ResponseDto>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<${serviceName}ResponseDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<${serviceName}ResponseDto>> create(
            @Valid @RequestBody ${serviceName}RequestDto request) {
        ${serviceName}ResponseDto created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("${serviceName} creado correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<${serviceName}ResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody ${serviceName}RequestDto request) {
        return ResponseEntity.ok(ApiResponse.ok("${serviceName} actualizado correctamente", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("${serviceName} eliminado correctamente"));
    }
}
