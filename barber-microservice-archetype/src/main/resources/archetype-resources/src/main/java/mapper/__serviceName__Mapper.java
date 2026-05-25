package ${package}.mapper;

import ${package}.dto.${serviceName}RequestDto;
import ${package}.dto.${serviceName}ResponseDto;
import ${package}.model.${serviceName};
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ${serviceName}Mapper {

    public ${serviceName}ResponseDto toResponseDto(${serviceName} entity) {
        return ${serviceName}ResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public List<${serviceName}ResponseDto> toResponseDtoList(List<${serviceName}> entities) {
        return entities.stream().map(this::toResponseDto).toList();
    }

    public ${serviceName} toEntity(${serviceName}RequestDto request) {
        return ${serviceName}.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public void updateEntity(${serviceName} entity, ${serviceName}RequestDto request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }
}
