package com.ecommerce.ms_reportes.controller;

import com.ecommerce.ms_reportes.dto.ReporteRequestDTO;
import com.ecommerce.ms_reportes.dto.ReporteResponseDTO;
import com.ecommerce.ms_reportes.service.ReporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReporteController.class)
class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReporteService reporteService;

    private ReporteResponseDTO reporteResponseDTO;
    private ReporteRequestDTO reporteRequestDTO;

    @BeforeEach
    void setUp() {
        reporteResponseDTO = ReporteResponseDTO.builder()
                .id(1)
                .titulo("Reporte Ventas Julio")
                .tipoReporte("VENTAS")
                .totalIngresos(15000.50f)
                .totalRegistros(120)
                .esConsolidado(true)
                .fechaGeneracion(LocalDate.of(2026, 7, 1))
                .build();

        reporteRequestDTO = ReporteRequestDTO.builder()
                .titulo("Reporte Ventas Julio")
                .tipoReporte("VENTAS")
                .totalIngresos(15000.50f)
                .totalRegistros(120)
                .esConsolidado(true)
                .fechaGeneracion(LocalDate.of(2026, 7, 1))
                .build();
    }

    // ============ GET /api/v1/reportes ============

    @Test
    void listarTodos_deberiaRetornar200ConListaDeReportes() throws Exception {
        when(reporteService.findAll()).thenReturn(List.of(reporteResponseDTO));

        mockMvc.perform(get("/api/v1/reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Reporte Ventas Julio"))
                .andExpect(jsonPath("$[0].tipoReporte").value("VENTAS"));
    }

    @Test
    void listarTodos_deberiaRetornar200ConListaVacia_cuandoNoHayReportes() throws Exception {
        when(reporteService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ============ GET /api/v1/reportes/{id} ============

    @Test
    void obtenerPorId_deberiaRetornar200ConElReporte() throws Exception {
        when(reporteService.findById(1)).thenReturn(reporteResponseDTO);

        mockMvc.perform(get("/api/v1/reportes/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Reporte Ventas Julio"));
    }

    @Test
    void obtenerPorId_deberiaPropagarError_cuandoNoExiste() throws Exception {
        when(reporteService.findById(99))
                .thenThrow(new RuntimeException("ResourceNotFound: Reporte no encontrado con ID: 99"));

        // Sin @ControllerAdvice, hoy termina en 500 aunque el Swagger documenta 404.
        mockMvc.perform(get("/api/v1/reportes/{id}", 99))
                .andExpect(status().is5xxServerError());
    }

    // ============ POST /api/v1/reportes ============

    @Test
    void crear_deberiaRetornar201ConElReporteCreado() throws Exception {
        when(reporteService.save(any(ReporteRequestDTO.class))).thenReturn(reporteResponseDTO);

        mockMvc.perform(post("/api/v1/reportes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(reporteRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Reporte Ventas Julio"));
    }

    @Test
    void crear_deberiaRetornar400_cuandoTituloEsVacio() throws Exception {
        ReporteRequestDTO dtoInvalido = ReporteRequestDTO.builder()
                .titulo("")
                .tipoReporte("VENTAS")
                .totalIngresos(100f)
                .totalRegistros(10)
                .esConsolidado(false)
                .fechaGeneracion(LocalDate.now())
                .build();

        mockMvc.perform(post("/api/v1/reportes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crear_deberiaRetornar400_cuandoFechaGeneracionEsFutura() throws Exception {
        ReporteRequestDTO dtoInvalido = ReporteRequestDTO.builder()
                .titulo("Reporte válido")
                .tipoReporte("VENTAS")
                .totalIngresos(100f)
                .totalRegistros(10)
                .esConsolidado(false)
                .fechaGeneracion(LocalDate.now().plusDays(5))
                .build();

        mockMvc.perform(post("/api/v1/reportes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crear_deberiaRetornar400_cuandoTotalIngresosEsNegativo() throws Exception {
        ReporteRequestDTO dtoInvalido = ReporteRequestDTO.builder()
                .titulo("Reporte válido")
                .tipoReporte("VENTAS")
                .totalIngresos(-500f)
                .totalRegistros(10)
                .esConsolidado(false)
                .fechaGeneracion(LocalDate.now())
                .build();

        mockMvc.perform(post("/api/v1/reportes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    // ============ PUT /api/v1/reportes/{id} ============

    @Test
    void actualizar_deberiaRetornar200ConElReporteActualizado() throws Exception {
        ReporteResponseDTO actualizado = ReporteResponseDTO.builder()
                .id(1)
                .titulo("Reporte Modificado")
                .tipoReporte("VENTAS")
                .totalIngresos(20000f)
                .totalRegistros(200)
                .esConsolidado(true)
                .fechaGeneracion(LocalDate.of(2026, 7, 1))
                .build();

        when(reporteService.update(eq(1), any(ReporteRequestDTO.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/reportes/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(reporteRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Reporte Modificado"))
                .andExpect(jsonPath("$.totalIngresos").value(20000f));
    }

    @Test
    void actualizar_deberiaRetornar400_cuandoDtoEsInvalido() throws Exception {
        ReporteRequestDTO dtoInvalido = ReporteRequestDTO.builder()
                .titulo("")
                .tipoReporte("")
                .fechaGeneracion(null)
                .build();

        mockMvc.perform(put("/api/v1/reportes/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    // ============ DELETE /api/v1/reportes/{id} ============

    @Test
    void eliminar_deberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/api/v1/reportes/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
