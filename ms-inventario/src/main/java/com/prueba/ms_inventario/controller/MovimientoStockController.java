package com.prueba.ms_inventario.controller;

import com.prueba.ms_inventario.dto.MovimientoStockRequestDTO;
import com.prueba.ms_inventario.dto.MovimientoStockResponseDTO;
import com.prueba.ms_inventario.service.MovimientoStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movimientos-stock")
@Slf4j
@Tag(name = "Movimientos de Stock", description = "API para registrar y consultar las entradas y salidas de productos del inventario")
public class MovimientoStockController {

    @Autowired
    private MovimientoStockService service;

    @Operation(summary = "Listar todos los movimientos de stock", description = "Obtiene el historial completo de entradas y salidas de inventario.")
    @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<MovimientoStockResponseDTO>> obtenerTodos() {
        log.info("/api/v1/movimientos-stock - Listando historial");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Operation(summary = "Obtener movimiento por ID", description = "Busca un registro específico en el historial de movimientos utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ID del movimiento no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoStockResponseDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("/api/v1/movimientos-stock/{} - Buscando registro", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Crear nuevo movimiento", description = "Registra una nueva entrada o salida de stock para un producto en específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o producto no encontrado")
    })
    @PostMapping
    public ResponseEntity<MovimientoStockResponseDTO> crear(@Valid @RequestBody MovimientoStockRequestDTO dto) {
        log.info("/api/v1/movimientos-stock - Creando nuevo movimiento");
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar movimiento manual", description = "Modifica los datos de un movimiento de stock previamente registrado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "El ID del movimiento no existe"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MovimientoStockResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody MovimientoStockRequestDTO dto) {
        log.info("/api/v1/movimientos-stock/{} - Actualizando datos manuales", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar movimiento de stock", description = "Elimina de forma permanente un registro del historial de movimientos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movimiento eliminado correctamente (Sin contenido)"),
            @ApiResponse(responseCode = "404", description = "El ID del movimiento no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("/api/v1/movimientos-stock/{} - Eliminando del historial", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}