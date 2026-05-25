package ${package}.controller;

import ${package}.dto.${serviceName}ResponseDto;
import ${package}.exception.GlobalExceptionHandler;
import ${package}.service.${serviceName}Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(${serviceName}Controller.class)
@Import(GlobalExceptionHandler.class)
class ${serviceName}ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ${serviceName}Service service;

    @Test
    void findAll_shouldReturn200() throws Exception {
        ${serviceName}ResponseDto dto = ${serviceName}ResponseDto.builder()
                .id(1L).name("Test").description("Desc").build();
        when(service.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/${apiPath}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Test"));
    }
}
