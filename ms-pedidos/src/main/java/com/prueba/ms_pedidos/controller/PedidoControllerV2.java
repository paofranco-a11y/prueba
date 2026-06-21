package com.prueba.ms_pedidos.controller;

import com.prueba.ms_pedidos.assemblers.PedidosModelAssemblers;
import com.prueba.ms_pedidos.model.Pedido;
import com.prueba.ms_pedidos.service.PedidoService;
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

@Slf4j
@RestController
@RequestMapping("/api/v2/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos V2 (HATEOAS)", description = "Gestión de pedidos con enlaces HATEOAS")
public class PedidoControllerV2 {

    private final PedidoService service;
    private final PedidosModelAssemblers assembler;

    @GetMapping
    @Operation(
            summary = "Listar todos los pedidos",
            description = "Retorna lista completa de pedidos con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> listarTodos() {
        log.info("/api/v2/pedidos - Listando todos los pedidos");

        List<EntityModel<Pedido>> pedidos = service.listarTodosModel()
                .stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Pedido>> collection = CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener pedido por ID",
            description = "Busca un pedido por su ID con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<EntityModel<Pedido>> buscarPorId(
            @Parameter(description = "ID del pedido", example = "1")
            @PathVariable Integer id) {
        log.info("/api/v2/pedidos/{} - Buscando pedido", id);

        Pedido pedido = service.obtenerModelPorId(id);
        return ResponseEntity.ok(assembler.toModel(pedido));
    }

    @PostMapping
    @Operation(
            summary = "Crear pedido",
            description = "Registra un nuevo pedido con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<EntityModel<Pedido>> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del pedido a crear"
            )
            @Valid @RequestBody Pedido pedido) {
        log.info("/api/v2/pedidos - Creando pedido");

        Pedido nuevo = service.crearModel(pedido);
        return new ResponseEntity<>(assembler.toModel(nuevo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar pedido",
            description = "Modifica un pedido existente con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<EntityModel<Pedido>> actualizar(
            @Parameter(description = "ID del pedido", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody Pedido pedido) {
        log.info("/api/v2/pedidos/{} - Actualizando pedido", id);

        Pedido actualizado = service.actualizarModel(id, pedido);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PutMapping("/{id}/estado")
    @Operation(
            summary = "Actualizar estado del pedido",
            description = "Cambia el estado de un pedido con enlaces HATEOAS"
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
        log.info("/api/v2/pedidos/{}/estado - Cambiando estado a {}", id, estado);
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
        log.info("/api/v2/pedidos/{} - Eliminando pedido", id);

        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}