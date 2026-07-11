package com.ecommerce.ms_reportes.controller;

import com.ecommerce.ms_reportes.assemblers.ReporteModelAssembler;
import com.ecommerce.ms_reportes.dto.ReporteRequestDTO;
import com.ecommerce.ms_reportes.model.Reporte;
import com.ecommerce.ms_reportes.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/reportes")
@Tag(name = "Reportes V2 - HATEOAS", description = "Endpoints de reportes adaptados al modelo Hypermedia (REST Maturity Level 3)")
public class ReporteControllerV2 {

    private final ReporteService reporteService;
    private final ReporteModelAssembler assembler;

    public ReporteControllerV2(ReporteService reporteService, ReporteModelAssembler assembler) {
        this.reporteService = reporteService;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar todos los reportes con enlaces HATEOAS", description = "Retorna una colección de reportes junto con hipervínculos dinámicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colección Hypermedia obtenida con éxito")
    })
    public ResponseEntity<CollectionModel<EntityModel<Reporte>>> listarTodos() {
        List<Reporte> reportes = reporteService.findAllEntities();

        List<EntityModel<Reporte>> reporteModels = reportes.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Reporte>> collectionModel = CollectionModel.of(reporteModels,
                linkTo(methodOn(ReporteControllerV2.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un reporte por ID con hipervínculos", description = "Recupera un reporte específico adjuntando enlaces contextuales de operaciones válidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado y enriquecido con HATEOAS"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado en el sistema")
    })
    public ResponseEntity<EntityModel<Reporte>> obtenerPorId(
            @Parameter(description = "ID único del reporte", required = true, example = "501")
            @PathVariable("id") Integer id) {

        Reporte reporte = reporteService.findEntityById(id);
        return ResponseEntity.ok(assembler.toModel(reporte));
    }

    @PostMapping
    @Operation(summary = "Generar un reporte en versión V2 (HATEOAS)", description = "Crea el reporte y añade la cabecera Location junto al payload hipermedia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Estructura de la petición inválida")
    })
    public ResponseEntity<EntityModel<Reporte>> crear(@Valid @RequestBody ReporteRequestDTO dto) {
        Reporte nuevoReporte = reporteService.saveEntity(dto);
        EntityModel<Reporte> entityModel = assembler.toModel(nuevoReporte);

        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los parámetros de un reporte (HATEOAS)", description = "Actualiza los campos individuales de un reporte y provee la estructura HATEOAS de respuesta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte modificado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada incorrectos"),
            @ApiResponse(responseCode = "404", description = "El reporte solicitado no existe")
    })
    public ResponseEntity<EntityModel<Reporte>> actualizar(
            @Parameter(description = "ID del reporte a modificar", required = true, example = "501")
            @PathVariable("id") Integer id,
            @Valid @RequestBody ReporteRequestDTO dto) {

        Reporte reporteActualizado = reporteService.updateEntity(id, dto);
        return ResponseEntity.ok(assembler.toModel(reporteActualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un reporte del sistema", description = "Borra el registro del reporte especificado sin retornar cuerpo en la respuesta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Reporte inviable o no localizado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del reporte a remover", required = true, example = "501")
            @PathVariable("id") Integer id) {

        reporteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}