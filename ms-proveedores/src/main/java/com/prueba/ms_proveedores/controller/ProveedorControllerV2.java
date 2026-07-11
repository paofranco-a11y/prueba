package com.prueba.ms_proveedores.controller;

import com.prueba.ms_proveedores.assemblers.ProveedorModelAssembler;
import com.prueba.ms_proveedores.dto.ProveedorDTO;
import com.prueba.ms_proveedores.dto.ProveedorRequestDTO;
import com.prueba.ms_proveedores.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/proveedores")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Proveedor V2 (HATEOAS)", description = "API versión 2 que incluye enlaces hipermedia para el proveedor")
public class ProveedorControllerV2 {

    private final ProveedorService proveedorService;
    private final ProveedorModelAssembler assembler;

    @Operation(summary = "Listar todos los proveedores", description = "Obtiene una lista completa de todos los registros de proveedores con enlaces HATEOAS.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProveedorDTO>>> listarTodos() {
        log.info("/api/v2/proveedores - Listar todos (HATEOAS)");
        List<ProveedorDTO> proveedores = proveedorService.listarTodos();

        // Convertimos cada ProveedorDTO en un modelo con vinculos
        List<EntityModel<ProveedorDTO>> proveedorModels = proveedores.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ProveedorDTO>> collectionModel = CollectionModel.of(proveedorModels,
                linkTo(methodOn(ProveedorControllerV2.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @Operation(summary = "Obtener proveedor por ID", description = "Busca un registro específico de proveedor utilizando su ID y agrega enlaces HATEOAS.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ID del proveedor no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProveedorDTO>> buscarPorId(@PathVariable Integer id) {
        log.info("/api/v2/proveedores/{} - Buscar por ID (HATEOAS)", id);
        ProveedorDTO dto = proveedorService.buscarPorId(id);
        // Devolvemos el objeto convertido por el Assembler
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @Operation(summary = "Crear nuevo proveedor", description = "Registra un nuevo proveedor en el sistema con sus datos válidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<ProveedorDTO> crear(@Valid @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        log.info("/api/v2/proveedores - Crea nuevo registro");
        ProveedorDTO creado = proveedorService.crear(proveedorRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar proveedor existente", description = "Actualiza los datos de un proveedor existente utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "El ID del proveedor no existe"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        log.info("/api/v2/proveedores/{} - Actualizando datos", id);
        return ResponseEntity.ok(proveedorService.actualizar(id, proveedorRequestDTO));
    }

    @Operation(summary = "Eliminar proveedor", description = "Elimina un registro de proveedor de forma permanente usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro eliminado correctamente (Sin contenido)"),
            @ApiResponse(responseCode = "404", description = "El ID del proveedor no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("/api/v2/proveedores/{} - Eliminando registro", id);
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar proveedores activos", description = "Obtiene una lista filtrada de todos los proveedores que se encuentran activos (HATEOAS).")
    @ApiResponse(responseCode = "200", description = "Búsqueda procesada correctamente")
    @GetMapping("/activos")
    public ResponseEntity<CollectionModel<EntityModel<ProveedorDTO>>> buscarProveedoresActivos() {
        log.info("/api/v2/proveedores/activos - Buscando proveedores activos (HATEOAS)");
        List<ProveedorDTO> activos = proveedorService.buscarProveedoresActivos();

        List<EntityModel<ProveedorDTO>> activosModels = activos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ProveedorDTO>> collectionModel = CollectionModel.of(activosModels,
                linkTo(methodOn(ProveedorControllerV2.class).buscarProveedoresActivos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }
}