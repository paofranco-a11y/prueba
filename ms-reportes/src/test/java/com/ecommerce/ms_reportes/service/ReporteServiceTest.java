package com.ecommerce.ms_reportes.service;

import com.ecommerce.ms_reportes.client.EnvioClient;
import com.ecommerce.ms_reportes.client.PagoClient;
import com.ecommerce.ms_reportes.client.PedidoClient;
import com.ecommerce.ms_reportes.dto.*;
import com.ecommerce.ms_reportes.model.Reporte;
import com.ecommerce.ms_reportes.repository.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private PedidoClient pedidoClient;

    @Mock
    private PagoClient pagoClient;

    @Mock
    private EnvioClient envioClient;

    @InjectMocks
    private ReporteService reporteService;

    private Reporte reporte;
    private ReporteRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        reporte = new Reporte(1, "Reporte Ventas Julio", "VENTAS", 15000.50f, 120, true, LocalDate.of(2026, 7, 1));

        requestDTO = ReporteRequestDTO.builder()
                .titulo("Reporte Ventas Julio")
                .tipoReporte("VENTAS")
                .totalIngresos(15000.50f)
                .totalRegistros(120)
                .esConsolidado(true)
                .fechaGeneracion(LocalDate.of(2026, 7, 1))
                .build();
    }

    // ============ findAll (V1) ============

    @Test
    void findAll_deberiaRetornarListaDeReportes() {
        when(reporteRepository.findAll()).thenReturn(List.of(reporte));

        var resultado = reporteService.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTitulo()).isEqualTo("Reporte Ventas Julio");
    }

    // ============ findById (V1) ============

    @Test
    void findById_deberiaRetornarReporte_cuandoExiste() {
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));

        var resultado = reporteService.findById(1);

        assertThat(resultado.getId()).isEqualTo(1);
    }

    @Test
    void findById_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(reporteRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reporteService.findById(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ResourceNotFound");
    }

    // ============ save (V1) - camino feliz con enriquecimiento ============

    @Test
    void save_deberiaConsolidarYEnriquecerConDatosDePedidoPagoEnvio() {
        PedidoResponseDTO pedido = new PedidoResponseDTO();
        pedido.setId(500);
        pedido.setClienteId(10);

        PagosResponseDTO pago = new PagosResponseDTO();
        pago.setId(700);
        pago.setEstado("APROBADO");

        EnvioResponseDTO envio = EnvioResponseDTO.builder()
                .id(900)
                .pedidoId(500)
                .direccionDestino("Av. Siempre Viva 742")
                .build();

        when(pedidoClient.obtenerPedidosParaReporte()).thenReturn(List.of(pedido));
        when(pagoClient.obtenerPagosParaReporte()).thenReturn(List.of(pago));
        when(envioClient.obtenerEnviosParaReporte()).thenReturn(List.of(envio));
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporte);

        var resultado = reporteService.save(requestDTO);

        assertThat(resultado.getTitulo()).isEqualTo("Reporte Ventas Julio");
        assertThat(resultado.getPedido()).isNotNull();
        assertThat(resultado.getPedido().getId()).isEqualTo(500);
        assertThat(resultado.getPago().getEstado()).isEqualTo("APROBADO");
        assertThat(resultado.getEnvio().getDireccionDestino()).isEqualTo("Av. Siempre Viva 742");
    }

    @Test
    void save_noDeberiaEnriquecer_cuandoListasExternasEstanVacias() {
        when(pedidoClient.obtenerPedidosParaReporte()).thenReturn(Collections.emptyList());
        when(pagoClient.obtenerPagosParaReporte()).thenReturn(Collections.emptyList());
        when(envioClient.obtenerEnviosParaReporte()).thenReturn(Collections.emptyList());
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporte);

        var resultado = reporteService.save(requestDTO);

        assertThat(resultado.getPedido()).isNull();
        assertThat(resultado.getPago()).isNull();
        assertThat(resultado.getEnvio()).isNull();
    }

    @Test
    void save_deberiaLanzarExcepcion_cuandoServicioExternoFalla() {
        when(pedidoClient.obtenerPedidosParaReporte())
                .thenThrow(new RuntimeException("Servicio no disponible"));

        assertThatThrownBy(() -> reporteService.save(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al consolidar el reporte");

        verify(reporteRepository, never()).save(any());
    }

    // ============ update (V1) ============

    @Test
    void update_deberiaActualizarCamposCorrectamente() {
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));
        when(reporteRepository.save(any(Reporte.class))).thenAnswer(inv -> inv.getArgument(0));

        requestDTO.setTitulo("Reporte Actualizado");
        var resultado = reporteService.update(1, requestDTO);

        assertThat(resultado.getTitulo()).isEqualTo("Reporte Actualizado");
    }

    @Test
    void update_deberiaLanzarExcepcion_cuandoReporteNoExiste() {
        when(reporteRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reporteService.update(1, requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no existente");
    }

    // ============ delete ============

    @Test
    void delete_deberiaEliminarReporte_cuandoExiste() {
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));

        reporteService.delete(1);

        verify(reporteRepository, times(1)).delete(reporte);
    }

    @Test
    void delete_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(reporteRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reporteService.delete(1))
                .isInstanceOf(RuntimeException.class);

        verify(reporteRepository, never()).delete(any());
    }

    // ============ V2 - findAllEntities / findEntityById ============

    @Test
    void findAllEntities_deberiaRetornarEntidadesCrudas() {
        when(reporteRepository.findAll()).thenReturn(List.of(reporte));

        var resultado = reporteService.findAllEntities();

        assertThat(resultado).containsExactly(reporte);
    }

    @Test
    void findEntityById_deberiaRetornarEntidad_cuandoExiste() {
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));

        var resultado = reporteService.findEntityById(1);

        assertThat(resultado).isEqualTo(reporte);
    }

    // ============ V2 - saveEntity / updateEntity ============

    @Test
    void saveEntity_deberiaGuardarEntidad_sinAnexarDTOsExternos() {
        when(pedidoClient.obtenerPedidosParaReporte()).thenReturn(Collections.emptyList());
        when(pagoClient.obtenerPagosParaReporte()).thenReturn(Collections.emptyList());
        when(envioClient.obtenerEnviosParaReporte()).thenReturn(Collections.emptyList());
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporte);

        var resultado = reporteService.saveEntity(requestDTO);

        assertThat(resultado).isEqualTo(reporte);
    }

    @Test
    void updateEntity_deberiaActualizarYRetornarEntidad() {
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));
        when(reporteRepository.save(any(Reporte.class))).thenAnswer(inv -> inv.getArgument(0));

        requestDTO.setTotalRegistros(500);
        var resultado = reporteService.updateEntity(1, requestDTO);

        assertThat(resultado.getTotalRegistros()).isEqualTo(500);
    }

    @Test
    void updateEntity_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(reporteRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reporteService.updateEntity(1, requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no existente para modificar");
    }
}
