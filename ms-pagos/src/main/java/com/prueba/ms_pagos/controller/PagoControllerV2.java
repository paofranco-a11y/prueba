package com.prueba.ms_pagos.controller;

import com.prueba.ms_pagos.assemblers.PagoModelAssembler;
import com.prueba.ms_pagos.model.Pagos;
import com.prueba.ms_pagos.service.PagosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/pagos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Pagos V2 (HATEOAS)", description = "Pagos con enlaces HATEOAS")
public class PagoControllerV2 {

    private final PagosService pagoService;
    private final PagoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Obtener todos los pagos", description = "Retorna lista de pagos con enlaces HATEOAS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CollectionModel<EntityModel<Pagos>>> listarTodos() {
        log.info("/api/v2/pagos - Solicitando todos los registros");

        List<EntityModel<Pagos>> pagos = pagoService.listarTodosModel()
                .stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Pagos>> collection = CollectionModel.of(pagos,
                linkTo(methodOn(PagoControllerV2.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID", description = "Busca un pago por su ID con enlaces HATEOAS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<EntityModel<Pagos>> obtenerPorId(
            @Parameter(description = "ID del pago", example = "1")
            @PathVariable("id") Integer id) {
        log.info("/api/v2/pagos/{} - Buscando pago", id);

        Pagos pago = pagoService.obtenerModelPorId(id);
        return ResponseEntity.ok(assembler.toModel(pago));
    }

    @PostMapping
    @Operation(summary = "Crear pago", description = "Registra un nuevo pago con enlaces HATEOAS")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<EntityModel<Pagos>> crear(
            @Valid @RequestBody Pagos pago) {
        log.info("/api/v2/pagos - Creando pago");

        Pagos nuevo = pagoService.crearModel(pago);
        return new ResponseEntity<>(assembler.toModel(nuevo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pago", description = "Modifica un pago existente con enlaces HATEOAS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<EntityModel<Pagos>> actualizar(
            @Parameter(description = "ID del pago", example = "1")
            @PathVariable("id") Integer id,
            @Valid @RequestBody Pagos pago) {
        log.info("/api/v2/pagos/{} - Actualizando pago", id);

        Pagos actualizado = pagoService.actualizarModel(id, pago);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pago", description = "Elimina un registro de pago")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pago", example = "1")
            @PathVariable("id") Integer id) {
        log.info("/api/v2/pagos/{} - Eliminando pago", id);

        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}