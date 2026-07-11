package com.ecommerce.ms_empleados.controller;

import com.ecommerce.ms_empleados.dto.EmpleadoRequestDTO;
import com.ecommerce.ms_empleados.dto.EmpleadoResponseDTO;
import com.ecommerce.ms_empleados.service.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmpleadoService empleadoService;

    private EmpleadoResponseDTO empleadoResponseDTO;
    private EmpleadoRequestDTO empleadoRequestDTO;

    @BeforeEach
    void setUp() {
        empleadoResponseDTO = new EmpleadoResponseDTO(
                1, "Juan Pérez", "juan.perez@ecommerce.com", 850000f, 3, true, LocalDate.of(2024, 3, 15));

        empleadoRequestDTO = new EmpleadoRequestDTO(
                "Juan Pérez", "juan.perez@ecommerce.com", 850000f, 3, true, LocalDate.of(2024, 3, 15));
    }

    // ============ GET /api/v1/empleados ============

    @Test
    void listarTodos_deberiaRetornar200ConListaDeEmpleados() throws Exception {
        when(empleadoService.findAll()).thenReturn(List.of(empleadoResponseDTO));

        mockMvc.perform(get("/api/v1/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombreCompleto").value("Juan Pérez"))
                .andExpect(jsonPath("$[0].correoElectronico").value("juan.perez@ecommerce.com"));
    }

    @Test
    void listarTodos_deberiaRetornar200ConListaVacia_cuandoNoHayEmpleados() throws Exception {
        when(empleadoService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ============ GET /api/v1/empleados/{id} ============

    @Test
    void obtenerPorId_deberiaRetornar200ConElEmpleado() throws Exception {
        when(empleadoService.findById(1)).thenReturn(empleadoResponseDTO);

        mockMvc.perform(get("/api/v1/empleados/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Pérez"));
    }

    @Test
    void obtenerPorId_deberiaPropagarError_cuandoNoExiste() throws Exception {
        when(empleadoService.findById(99))
                .thenThrow(new RuntimeException("ResourceNotFound: Empleado no encontrado con ID: 99"));

        // Nota: sin un @ControllerAdvice que traduzca la excepción, esto hoy
        // termina en un 500, no en 404. Ver comentario al final del archivo.
        mockMvc.perform(get("/api/v1/empleados/{id}", 99))
                .andExpect(status().is5xxServerError());
    }

    // ============ POST /api/v1/empleados ============

    @Test
    void crear_deberiaRetornar201ConElEmpleadoCreado() throws Exception {
        when(empleadoService.save(any(EmpleadoRequestDTO.class))).thenReturn(empleadoResponseDTO);

        mockMvc.perform(post("/api/v1/empleados")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(empleadoRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Pérez"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crear_deberiaRetornar400_cuandoDtoEsInvalido() throws Exception {
        EmpleadoRequestDTO dtoInvalido = new EmpleadoRequestDTO(
                "", "correo-invalido", null, 0, true, LocalDate.now().plusDays(5));

        mockMvc.perform(post("/api/v1/empleados")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crear_deberiaRetornar400_cuandoSueldoBaseEsNegativo() throws Exception {
        EmpleadoRequestDTO dtoInvalido = new EmpleadoRequestDTO(
                "Juan Pérez", "juan.perez@ecommerce.com", -500f, 3, true, LocalDate.of(2024, 3, 15));

        mockMvc.perform(post("/api/v1/empleados")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    // ============ PUT /api/v1/empleados/{id} ============

    @Test
    void actualizar_deberiaRetornar200ConElEmpleadoActualizado() throws Exception {
        EmpleadoResponseDTO actualizado = new EmpleadoResponseDTO(
                1, "Juan Pérez Editado", "juan.perez@ecommerce.com", 900000f, 3, true, LocalDate.of(2024, 3, 15));

        when(empleadoService.update(eq(1), any(EmpleadoRequestDTO.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/empleados/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(empleadoRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Pérez Editado"))
                .andExpect(jsonPath("$.sueldoBase").value(900000f));
    }

    @Test
    void actualizar_deberiaRetornar400_cuandoDtoEsInvalido() throws Exception {
        EmpleadoRequestDTO dtoInvalido = new EmpleadoRequestDTO(
                "", "correo-invalido", null, 0, true, LocalDate.now().plusDays(5));

        mockMvc.perform(put("/api/v1/empleados/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    // ============ DELETE /api/v1/empleados/{id} ============

    @Test
    void eliminar_deberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/api/v1/empleados/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
