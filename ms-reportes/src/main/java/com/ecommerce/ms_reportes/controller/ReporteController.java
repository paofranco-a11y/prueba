package com.ecommerce.ms_reportes.controller;

import com.ecommerce.ms_reportes.dto.ReporteRequestDTO;
import com.ecommerce.ms_reportes.dto.ReporteResponseDTO;
import com.ecommerce.ms_reportes.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "API para la generación, consulta y administración de reportes estadísticos y ejecutivos")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los reportes", description = "Retorna una lista completa con todos los reportes generados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida con éxito")
    })
    public ResponseEntity<List<ReporteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(reporteService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un reporte por ID", description = "Busca y devuelve la información detallada de un reporte específico a través de su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte localizado con éxito"),
            @ApiResponse(responseCode = "404", description = "El reporte solicitado no existe")
    })
    public ResponseEntity<ReporteResponseDTO> obtenerPorId(
            @Parameter(description = "ID del reporte a consultar", required = true, example = "501")
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(reporteService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Generar un nuevo reporte", description = "Crea y procesa un nuevo reporte basado en los parámetros e indicadores provistos en el cuerpo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado y procesado con éxito"),
            @ApiResponse(responseCode = "400", description = "Estructura del payload de la petición inválida o incompleta")
    })
    public ResponseEntity<ReporteResponseDTO> crear(@Valid @RequestBody ReporteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteService.save(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los parámetros de un reporte", description = "Modifica los criterios o información de un reporte existente según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte modificado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Reporte no localizado para actualizar")
    })
    public ResponseEntity<ReporteResponseDTO> actualizar(
            @Parameter(description = "ID del reporte a modificar", required = true, example = "501")
            @PathVariable("id") Integer id,
            @Valid @RequestBody ReporteRequestDTO dto) {
        return ResponseEntity.ok(reporteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un reporte", description = "Remueve del sistema el registro histórico del reporte especificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "El reporte a eliminar no existe")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del reporte a eliminar", required = true, example = "501")
            @PathVariable("id") Integer id) {
        reporteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}