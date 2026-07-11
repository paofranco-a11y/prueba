package com.ecommerce.ms_empleados.controller;

import com.ecommerce.ms_empleados.assemblers.EmpleadoModelAssembler;
import com.ecommerce.ms_empleados.dto.EmpleadoRequestDTO;
import com.ecommerce.ms_empleados.model.Empleado;
import com.ecommerce.ms_empleados.service.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoControllerV2Test {

    @Mock
    private EmpleadoService empleadoService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        // Usamos el assembler real (no mock) para validar que los links HATEOAS se generan correctamente
        EmpleadoModelAssembler assembler = new EmpleadoModelAssembler();
        EmpleadoControllerV2 controller = new EmpleadoControllerV2(empleadoService, assembler);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        empleado = new Empleado(1, "Juan Pérez", "juan.perez@ecommerce.com", 850000f, 3, true, LocalDate.of(2024, 3, 15));
    }

    @Test
    void listarTodos_deberiaRetornar200ConColeccionYLinks() throws Exception {
        when(empleadoService.findAllEntities()).thenReturn(List.of(empleado));

        mockMvc.perform(get("/api/v2/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.empleadoList[0].nombreCompleto").value("Juan Pérez"))
                .andExpect(jsonPath("$._embedded.empleadoList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void obtenerPorId_deberiaRetornar200ConLinksHateoas() throws Exception {
        when(empleadoService.findEntityById(1)).thenReturn(empleado);

        mockMvc.perform(get("/api/v2/empleados/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Pérez"))
                .andExpect(jsonPath("$._links.self.href").value(endsWith("/api/v2/empleados/1")))
                .andExpect(jsonPath("$._links.empleados.href").exists());
    }

    @Test
    void crear_deberiaRetornar201ConLocationHeader() throws Exception {
        EmpleadoRequestDTO dto = new EmpleadoRequestDTO(
                "Juan Pérez", "juan.perez@ecommerce.com", 850000f, 3, true, LocalDate.of(2024, 3, 15));

        when(empleadoService.saveEntity(any(EmpleadoRequestDTO.class))).thenReturn(empleado);

        mockMvc.perform(post("/api/v2/empleados")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Pérez"));
    }

    @Test
    void crear_deberiaRetornar400_cuandoDtoEsInvalido() throws Exception {
        // nombreCompleto vacío, correo inválido, sueldoBase nulo → dispara @Valid
        EmpleadoRequestDTO dtoInvalido = new EmpleadoRequestDTO(
                "", "correo-invalido", null, 0, true, LocalDate.now().plusDays(5));

        mockMvc.perform(post("/api/v2/empleados")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_deberiaRetornar200ConEmpleadoActualizado() throws Exception {
        Empleado actualizado = new Empleado(1, "Juan Pérez Editado", "juan.perez@ecommerce.com", 900000f, 3, true, LocalDate.of(2024, 3, 15));

        EmpleadoRequestDTO dto = new EmpleadoRequestDTO(
                "Juan Pérez Editado", "juan.perez@ecommerce.com", 900000f, 3, true, LocalDate.of(2024, 3, 15));

        when(empleadoService.updateEntity(eq(1), any(EmpleadoRequestDTO.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v2/empleados/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Pérez Editado"));
    }

    @Test
    void eliminar_deberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/api/v2/empleados/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
