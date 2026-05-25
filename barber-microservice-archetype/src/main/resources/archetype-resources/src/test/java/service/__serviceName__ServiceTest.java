package ${package}.service;

import ${package}.dto.${serviceName}RequestDto;
import ${package}.dto.${serviceName}ResponseDto;
import ${package}.exception.${serviceName}NotFoundException;
import ${package}.mapper.${serviceName}Mapper;
import ${package}.model.${serviceName};
import ${package}.repository.${serviceName}Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ${serviceName}ServiceTest {

    @Mock
    private ${serviceName}Repository repository;

    private ${serviceName}ServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ${serviceName}ServiceImpl(repository, new ${serviceName}Mapper());
    }

    @Test
    void findAll_shouldReturnList() {
        ${serviceName} entity = ${serviceName}.builder().id(1L).name("Test").description("Desc").build();
        when(repository.findAll()).thenReturn(List.of(entity));

        List<${serviceName}ResponseDto> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findById_whenNotFound_shouldThrow() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(${serviceName}NotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void create_shouldSave() {
        ${serviceName}RequestDto request = ${serviceName}RequestDto.builder()
                .name("Nuevo").description("Descripción").build();
        when(repository.save(any(${serviceName}.class))).thenAnswer(inv -> {
            ${serviceName} e = inv.getArgument(0);
            e.setId(1L);
            return e;
        });

        ${serviceName}ResponseDto result = service.create(request);

        assertNotNull(result);
        assertEquals("Nuevo", result.getName());
        verify(repository).save(any(${serviceName}.class));
    }
}
