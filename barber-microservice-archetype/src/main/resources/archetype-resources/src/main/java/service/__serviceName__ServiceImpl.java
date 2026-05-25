package ${package}.service;

import ${package}.dto.${serviceName}RequestDto;
import ${package}.dto.${serviceName}ResponseDto;
import ${package}.exception.${serviceName}NotFoundException;
import ${package}.mapper.${serviceName}Mapper;
import ${package}.model.${serviceName};
import ${package}.repository.${serviceName}Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ${serviceName}ServiceImpl implements ${serviceName}Service {

    private final ${serviceName}Repository repository;
    private final ${serviceName}Mapper mapper;

    public ${serviceName}ServiceImpl(${serviceName}Repository repository, ${serviceName}Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<${serviceName}ResponseDto> findAll() {
        return mapper.toResponseDtoList(repository.findAll());
    }

    @Override
    public ${serviceName}ResponseDto findById(Long id) {
        ${serviceName} entity = repository.findById(id)
                .orElseThrow(() -> new ${serviceName}NotFoundException(id));
        return mapper.toResponseDto(entity);
    }

    @Override
    @Transactional
    public ${serviceName}ResponseDto create(${serviceName}RequestDto request) {
        ${serviceName} saved = repository.save(mapper.toEntity(request));
        return mapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public ${serviceName}ResponseDto update(Long id, ${serviceName}RequestDto request) {
        ${serviceName} entity = repository.findById(id)
                .orElseThrow(() -> new ${serviceName}NotFoundException(id));
        mapper.updateEntity(entity, request);
        return mapper.toResponseDto(repository.save(entity));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ${serviceName}NotFoundException(id);
        }
        repository.deleteById(id);
    }
}
