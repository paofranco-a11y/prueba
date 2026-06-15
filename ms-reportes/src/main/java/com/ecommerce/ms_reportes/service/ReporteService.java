package com.ecommerce.ms_reportes.service;


import com.ecommerce.ms_reportes.client.EnvioClient;
import com.ecommerce.ms_reportes.client.PagoClient;
import com.ecommerce.ms_reportes.client.PedidoClient;
import com.ecommerce.ms_reportes.dto.ReporteRequestDTO;
import com.ecommerce.ms_reportes.dto.ReporteResponseDTO;
import com.ecommerce.ms_reportes.mapper.ReporteMapper;
import com.ecommerce.ms_reportes.model.Reporte;
import com.ecommerce.ms_reportes.repository.ReporteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private static final Logger log = LoggerFactory.getLogger(ReporteService.class);

    private final ReporteRepository reporteRepository;
    private final PedidoClient pedidoClient;
    private final PagoClient pagoClient;
    private final EnvioClient envioClient;

    public ReporteService(ReporteRepository reporteRepository, PedidoClient pedidoClient,
                          PagoClient pagoClient, EnvioClient envioClient) {
        this.reporteRepository = reporteRepository;
        this.pedidoClient = pedidoClient;
        this.pagoClient = pagoClient;
        this.envioClient = envioClient;
    }

    public List<ReporteResponseDTO> findAll() {
        log.info("Ejecutando método findAll en ReporteService");
        return reporteRepository.findAll().stream()
                .map(ReporteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ReporteResponseDTO findById(Integer id) {
        log.info("Ejecutando método findById en ReporteService para ID: {}", id);
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ResourceNotFound: Reporte no encontrado con ID: " + id));
        return ReporteMapper.toDTO(reporte);
    }

    public ReporteResponseDTO save(ReporteRequestDTO dto) {
        log.info("Ejecutando método save para crear un nuevo reporte de tipo: {}", dto.getTipoReporte());
        try {

            List<Object> pedidos = pedidoClient.obtenerPedidosParaReporte();
            List<Object> pagos = pagoClient.obtenerPagosParaReporte();
            List<Object> envios = envioClient.obtenerEnviosParaReporte();

            log.info("Datos consolidados exitosamente. Pedidos: {}, Pagos: {}, Envíos: {}",
                    pedidos.size(), pagos.size(), envios.size());

            Reporte reporte = ReporteMapper.toEntity(dto);
            Reporte guardado = reporteRepository.save(reporte);
            return ReporteMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Error crítico en la comunicación con microservicios al consolidar reporte: {}", e.getMessage());
            throw new RuntimeException("Error al consolidar el reporte. Verifique que los microservicios externos estén operativos.");
        }
    }

    public ReporteResponseDTO update(Integer id, ReporteRequestDTO dto) {
        log.info("Ejecutando método update para el Reporte con ID: {}", id);
        Reporte existente = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ResourceNotFound: Reporte no existente"));

        existente.setTitulo(dto.getTitulo());
        existente.setTipoReporte(dto.getTipoReporte());
        existente.setTotalIngresos(dto.getTotalIngresos());
        existente.setTotalRegistros(dto.getTotalRegistros());
        existente.setEsConsolidado(dto.isEsConsolidado());
        existente.setFechaGeneracion(dto.getFechaGeneracion());

        return ReporteMapper.toDTO(reporteRepository.save(existente));
    }

    public void delete(Integer id) {
        log.info("Ejecutando método delete para el Reporte con ID: {}", id);
        Reporte existente = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ResourceNotFound: Reporte no encontrado"));
        reporteRepository.delete(existente);
    }
}
