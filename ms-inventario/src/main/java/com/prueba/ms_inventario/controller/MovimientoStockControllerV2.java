package com.prueba.ms_inventario.controller;

import com.prueba.ms_inventario.assemblers.MovimientoStockModelAssembler;
import com.prueba.ms_inventario.dto.MovimientoStockResponseDTO;
import com.prueba.ms_inventario.service.MovimientoStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/movimientos-stock")
@Slf4j
@Tag(name = "Movimientos de Stock V2 (HATEOAS)", description = "API versión 2 que incluye enlaces hipermedia")
public class MovimientoStockControllerV2 {

    @Autowired
    private MovimientoStockService service;

    @Autowired
    private MovimientoStockModelAssembler assembler;

    @Operation(summary = "Listar todos los movimientos (HATEOAS)")
    @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<MovimientoStockResponseDTO>>> obtenerTodos() {
        log.info("/api/v2/movimientos-stock - Listando historial con HATEOAS");

        List<EntityModel<MovimientoStockResponseDTO>> movimientos = service.obtenerTodos().stream()
                .map(assembler::convertir)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<MovimientoStockResponseDTO>> collectionModel = CollectionModel.of(movimientos,
                linkTo(methodOn(MovimientoStockControllerV2.class).obtenerTodos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @Operation(summary = "Obtener movimiento por ID (HATEOAS)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "El ID no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<MovimientoStockResponseDTO>> obtenerPorId(@PathVariable Integer id) {
        log.info("/api/v2/movimientos-stock/{} - Buscando registro con HATEOAS", id);
        MovimientoStockResponseDTO dto = service.obtenerPorId(id);
        return ResponseEntity.ok(assembler.convertir(dto));
    }
}