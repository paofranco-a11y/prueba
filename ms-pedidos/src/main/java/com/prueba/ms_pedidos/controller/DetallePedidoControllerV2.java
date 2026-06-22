package com.prueba.ms_pedidos.controller;

import com.prueba.ms_pedidos.assemblers.DetallePedidoModelAssembler;
import com.prueba.ms_pedidos.model.DetallePedido;
import com.prueba.ms_pedidos.service.DetallePedidoService;
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
@RequestMapping("/api/v2/detalles-pedido")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Detalles de Pedido V2 (HATEOAS)", description = "Gestión de detalles de pedidos con enlaces HATEOAS")
public class DetallePedidoControllerV2 {

    private final DetallePedidoService service;
    private final DetallePedidoModelAssembler assembler;

    @GetMapping
    @Operation(
            summary = "Listar todos los detalles",
            description = "Retorna lista completa de detalles de pedidos con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CollectionModel<EntityModel<DetallePedido>>> obtenerTodos() {
        log.info("/api/v2/detalles-pedido - Solicitando listado completo");

        List<EntityModel<DetallePedido>> detalles = service.listarTodosModel()
                .stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<DetallePedido>> collection = CollectionModel.of(detalles,
                linkTo(methodOn(DetallePedidoControllerV2.class).obtenerTodos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener detalle por ID",
            description = "Busca un detalle de pedido por su ID con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<EntityModel<DetallePedido>> obtenerPorId(
            @Parameter(description = "ID del detalle de pedido", example = "1")
            @PathVariable Integer id) {
        log.info("/api/v2/detalles-pedido/{} - Buscando registro", id);

        DetallePedido detalle = service.obtenerModelPorId(id);
        return ResponseEntity.ok(assembler.toModel(detalle));
    }

    @PostMapping
    @Operation(
            summary = "Crear detalle de pedido",
            description = "Registra un nuevo ítem con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Detalle creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<EntityModel<DetallePedido>> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del detalle a crear"
            )
            @Valid @RequestBody DetallePedido detalle) {
        log.info("/api/v2/detalles-pedido - Agregando nuevo item");

        DetallePedido nuevo = service.crearModel(detalle);
        return new ResponseEntity<>(assembler.toModel(nuevo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar detalle de pedido",
            description = "Modifica un detalle de pedido existente con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<EntityModel<DetallePedido>> actualizar(
            @Parameter(description = "ID del detalle de pedido", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody DetallePedido detalle) {
        log.info("/api/v2/detalles-pedido/{} - Solicitud de edicion", id);

        DetallePedido actualizado = service.actualizarModel(id, detalle);
        return ResponseEntity.ok(assembler.toModel(actualizado));
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
        log.info("/api/v2/detalles-pedido/{} - Solicitud de eliminacion", id);

        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
