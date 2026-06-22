package com.prueba.ms_pagos.controller;


import com.prueba.ms_pagos.dto.PagosRequestDTO;
import com.prueba.ms_pagos.dto.PagosResponseDTO;
import com.prueba.ms_pagos.service.PagosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Pagos", description = "Pagos de ventas realizadas en el comercio electronico")
public class PagosController {

    private final PagosService pagoService;

    @GetMapping
    @Operation(
            summary = "Obtener todos los pagos",
            description = "Retorna la lista completa de pagos confirmados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PagosResponseDTO>> listarTodos() {
        log.info("/api/v1/pagos - Solicitando todos los registros");
        return ResponseEntity.ok(pagoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener pago por ID",
            description = "Busca un pago específico por su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PagosResponseDTO> obtenerPorId(
            @Parameter(description = "ID del pago", example = "1")
            @PathVariable("id") Integer id) {
        log.info("/api/v1/pagos/{} - Buscando pago", id);
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(
            summary = "Crear pago",
            description = "Registra un nuevo pago para un pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PagosResponseDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del pago a crear"
            )
            @Valid @RequestBody PagosRequestDTO dto) {
        log.info("/api/v1/pagos - Creando pago para pedido ID: {}", dto.getPedidoId());
        return new ResponseEntity<>(pagoService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar pago",
            description = "Modifica los datos de un pago existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagosResponseDTO> actualizar(
            @Parameter(description = "ID del pago", example = "1")
            @PathVariable("id") Integer id,
            @Valid @RequestBody PagosRequestDTO dto) {
        log.info("/api/v1/pagos/{} - Actualizando datos", id);
        return ResponseEntity.ok(pagoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar pago",
            description = "Elimina permanentemente un registro de pago"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pago", example = "1")
            @PathVariable("id") Integer id) {
        log.info("/api/v1/pagos/{} - Eliminando registro", id);
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}