package com.ecommerce.ms_envios.controller;

import com.ecommerce.ms_envios.assemblers.EnvioModelAssembler;
import com.ecommerce.ms_envios.dto.EnvioResponseDTO;
import com.ecommerce.ms_envios.service.EnvioService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EnvioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EnvioService envioService;

    @Mock
    private EnvioModelAssembler assembler;

    @InjectMocks
    private EnvioController envioController; // V1

    @InjectMocks
    private EnvioControllerV2 envioControllerV2; // V2

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(envioController, envioControllerV2).build();
    }

    @Test
    void testListarTodos_V1_Http200() throws Exception {
        EnvioResponseDTO dto = new EnvioResponseDTO();
        dto.setId(1);
        when(envioService.findAll()).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/v1/envios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testListarTodos_V2_HATEOAS_Http200() throws Exception {
        EnvioResponseDTO dto = new EnvioResponseDTO();
        dto.setId(1);

        when(envioService.findAll()).thenReturn(Collections.singletonList(dto));
        // Mockeamos el assembler para que devuelva un modelo con HATEOAS
        when(assembler.toModel(any(EnvioResponseDTO.class))).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v2/envios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.envioResponseDTOList[0].id").value(1));
    }
}