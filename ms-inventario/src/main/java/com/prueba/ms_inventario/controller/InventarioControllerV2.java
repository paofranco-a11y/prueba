package com.prueba.ms_inventario.controller;

import com.prueba.ms_inventario.assemblers.InventarioModelAssembler;
import com.prueba.ms_inventario.dto.InventarioResponseDTO;
import com.prueba.ms_inventario.service.InventarioService;
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
@RequestMapping("/api/v2/inventario")
@Slf4j
@Tag(name = "Inventario V2 (HATEOAS)", description = "API versión 2 que incluye enlaces hipermedia para el inventario")
public class InventarioControllerV2 {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioModelAssembler assembler;

    @Operation(summary = "Listar todo el inventario (HATEOAS)", description = "Obtiene una lista completa con enlaces HATEOAS.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<InventarioResponseDTO>>> listarTodos() {
        log.info("/api/v2/inventario - Listar todos con HATEOAS");

        List<EntityModel<InventarioResponseDTO>> inventarios = inventarioService.listarTodos().stream()
                .map(assembler::convertir)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<InventarioResponseDTO>> collectionModel = CollectionModel.of(inventarios,
                linkTo(methodOn(InventarioControllerV2.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @Operation(summary = "Obtener inventario por ID (HATEOAS)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ID del inventario no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<InventarioResponseDTO>> obtenerPorId(@PathVariable Integer id) {
        log.info("/api/v2/inventario/{} - Buscar por ID con HATEOAS", id);
        InventarioResponseDTO dto = inventarioService.obtenerPorId(id);
        return ResponseEntity.ok(assembler.convertir(dto));
    }
}