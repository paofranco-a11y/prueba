package com.prueba.ms_pedidos.controller;

import com.prueba.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.prueba.ms_pedidos.dto.DetallePedidoResponseDTO;
import com.prueba.ms_pedidos.service.DetallePedidoService;
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

@RestController
@RequestMapping("/api/v1/detalles-pedido")
@Slf4j
@Tag(name = "Detalles de Pedido", description = "Gestión de detalles de pedidos del e-commerce")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService service;

    @GetMapping
    @Operation(
            summary = "Listar todos los detalles",
            description = "Retorna la lista completa de detalles de pedidos registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<DetallePedidoResponseDTO>> obtenerTodos() {
        log.info("/api/v1/detalles-pedido - Solicitando listado completo");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener detalle por ID",
            description = "Busca y retorna un detalle de pedido específico por su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DetallePedidoResponseDTO> obtenerPorId(
            @Parameter(description = "ID del detalle de pedido", example = "1")
            @PathVariable Integer id) {
        log.info("/api/v1/detalles-pedido/{} - Buscando registro", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    @Operation(
            summary = "Crear detalle de pedido",
            description = "Registra un nuevo ítem en el detalle de un pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Detalle creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DetallePedidoResponseDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del detalle de pedido a crear"
            )
            @Valid @RequestBody DetallePedidoRequestDTO dto) {
        log.info("/api/v1/detalles-pedido - Agregando nuevo item");
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar detalle de pedido",
            description = "Modifica la información de un detalle de pedido existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<DetallePedidoResponseDTO> actualizar(
            @Parameter(description = "ID del detalle de pedido", example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del detalle"
            )
            @Valid @RequestBody DetallePedidoRequestDTO dto) {
        log.info("/api/v1/detalles-pedido/{} - Solicitud de edicion", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar detalle de pedido",
            description = "Elimina físicamente un detalle de pedido por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Detalle eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del detalle de pedido", example = "1")
            @PathVariable Integer id) {
        log.info("/api/v1/detalles-pedido/{} - Solicitud de eliminacion", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}