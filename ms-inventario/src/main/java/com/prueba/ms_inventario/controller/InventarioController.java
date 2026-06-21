package com.prueba.ms_inventario.controller;

import com.prueba.ms_inventario.dto.InventarioRequestDTO;
import com.prueba.ms_inventario.dto.InventarioResponseDTO;
import com.prueba.ms_inventario.service.InventarioService;
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
@RequestMapping("/api/v1/inventario")
@Slf4j
@Tag(name = "Inventario", description = "API para la gestión y consulta del inventario de productos")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Operation(summary = "Listar todo el inventario", description = "Obtiene una lista completa de todos los registros en el inventario.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> listarTodos() {
        log.info("/api/v1/inventario - Listar todos");
        return ResponseEntity.ok(inventarioService.listarTodos());
    }

    @Operation(summary = "Obtener inventario por ID", description = "Busca un registro específico en el inventario utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ID del inventario no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("/api/v1/inventario/{} - Buscar por ID", id);
        return ResponseEntity.ok(inventarioService.obtenerPorId(id));
    }

    @Operation(summary = "Crear nuevo registro de inventario", description = "Registra un nuevo producto en el inventario con su cantidad inicial.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<InventarioResponseDTO> guardar(@Valid @RequestBody InventarioRequestDTO dto) {
        log.info("/api/v1/inventario - Crea nuevo registro");
        InventarioResponseDTO creado = inventarioService.crear(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar registro de inventario", description = "Actualiza los datos de un registro de inventario existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "El ID del inventario no existe"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> actualizar(
            @PathVariable("id") Integer id,
            @Valid @RequestBody InventarioRequestDTO dto) {
        log.info("/api/v1/inventario/{} - Actualizando datos", id);
        return ResponseEntity.ok(inventarioService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar registro de inventario", description = "Elimina un registro del inventario de forma permanente usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro eliminado correctamente (Sin contenido)"),
            @ApiResponse(responseCode = "404", description = "El ID del inventario no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}