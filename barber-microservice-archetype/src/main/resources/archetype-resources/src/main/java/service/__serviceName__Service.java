package ${package}.service;

import ${package}.dto.${serviceName}RequestDto;
import ${package}.dto.${serviceName}ResponseDto;

import java.util.List;

public interface ${serviceName}Service {

    List<${serviceName}ResponseDto> findAll();

    ${serviceName}ResponseDto findById(Long id);

    ${serviceName}ResponseDto create(${serviceName}RequestDto request);

    ${serviceName}ResponseDto update(Long id, ${serviceName}RequestDto request);

    void deleteById(Long id);
}
