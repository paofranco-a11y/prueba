package com.prueba.ms_pedidos.controller;

import com.prueba.ms_pedidos.dto.PedidoRequestDTO;
import com.prueba.ms_pedidos.dto.PedidoResponseDTO;
import com.prueba.ms_pedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Gestión de pedidos del e-commerce")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    @Operation(
            summary = "Listar todos los pedidos",
            description = "Retorna la lista completa de pedidos registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener pedido por ID",
            description = "Busca y retorna un pedido específico por su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @Parameter(description = "ID del pedido", example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    @Operation(
            summary = "Crear pedido",
            description = "Registra un nuevo pedido en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PedidoResponseDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del pedido a crear"
            )
            @Valid @RequestBody PedidoRequestDTO request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @GetMapping("/pagados")
    @Operation(
            summary = "Listar pedidos pagados",
            description = "Retorna todos los pedidos con estado pagado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PedidoResponseDTO>> listarPagados() {
        return ResponseEntity.ok(service.obtenerPagados());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar pedido",
            description = "Modifica los datos completos de un pedido existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<PedidoResponseDTO> actualizar(
            @Parameter(description = "ID del pedido", example = "1")
            @PathVariable Integer id,
            @RequestBody PedidoRequestDTO dto) {
        log.info("Endpoint: PUT /api/v1/pedidos/{} - Actualizacion completa de datos", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PutMapping("/{id}/estado")
    @Operation(
            summary = "Actualizar estado del pedido",
            description = "Cambia el estado de un pedido, usado por ms-pagos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<Void> actualizarEstado(
            @Parameter(description = "ID del pedido", example = "1")
            @PathVariable Integer id,
            @Parameter(description = "Nuevo estado del pedido", example = "Pagado")
            @RequestParam String estado) {
        log.info("/api/v1/pedidos/{}/estado - Cambiando estado a {}", id, estado);
        service.actualizarEstado(id, estado);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar pedido",
            description = "Elimina físicamente un pedido por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pedido", example = "1")
            @PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}