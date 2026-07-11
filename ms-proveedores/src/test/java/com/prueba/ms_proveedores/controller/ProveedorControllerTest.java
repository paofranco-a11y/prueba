package com.prueba.ms_proveedores.controller;

import com.prueba.ms_proveedores.dto.ProveedorDTO;
import com.prueba.ms_proveedores.exception.ResourceNotFoundException;
import com.prueba.ms_proveedores.service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProveedorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProveedorService proveedorService;

    @InjectMocks
    private ProveedorController proveedorController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(proveedorController)
                .setControllerAdvice(new com.prueba.ms_proveedores.exception.GlobalExceptionHandler()) // Captura errores estructurados
                .build();
    }

    @Test
    void testBuscarPorId_Http200_Exitoso() throws Exception {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(1);
        dto.setNombre("Logística Central S.A.");

        when(proveedorService.buscarPorId(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Logística Central S.A."));
    }

    @Test
    void testBuscarPorId_Http404_NoEncontrado() throws Exception {
        when(proveedorService.buscarPorId(99)).thenThrow(new ResourceNotFoundException("Proveedor no encontrado"));

        mockMvc.perform(get("/api/v1/proveedores/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource Not Found"));
    }
}