package com.prueba.ms_proveedores.controller;

import com.prueba.ms_proveedores.assemblers.ContratoModelAssembler;
import com.prueba.ms_proveedores.dto.ContratoDTO;
import com.prueba.ms_proveedores.exception.GlobalExceptionHandler;
import com.prueba.ms_proveedores.exception.ResourceNotFoundException;
import com.prueba.ms_proveedores.service.ContratoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ContratoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContratoService contratoService;

    @Mock
    private ContratoModelAssembler assembler;

    @InjectMocks
    private ContratoControllerV2 contratoControllerV2;

    @BeforeEach
    void setUp() {
        // Enlazamos el controlador V2 con el manejador de excepciones global para interceptar los errores estructurados
        mockMvc = MockMvcBuilders.standaloneSetup(contratoControllerV2)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testListarTodos_Http200_Exitoso() throws Exception {
        ContratoDTO dto = new ContratoDTO();
        dto.setId(1);
        dto.setCodigoContrato("CON-2026-01");

        when(contratoService.listarTodos()).thenReturn(Collections.singletonList(dto));
        when(assembler.toModel(any(ContratoDTO.class))).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v2/contratos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.contratoDTOList[0].codigoContrato").value("CON-2026-01"));
    }

    @Test
    void testBuscarPorId_Http404_ContratoNoExiste() throws Exception {
        // Simulamos que el servicio arroja la excepción cuando se busca un ID que no existe
        when(contratoService.buscarPorId(99)).thenThrow(new ResourceNotFoundException("Contrato no encontrado con el ID: 99"));

        mockMvc.perform(get("/api/v2/contratos/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Resource Not Found"))
                .andExpect(jsonPath("$.message").value("Contrato no encontrado con el ID: 99"));
    }
}