package com.prueba.ms_proveedores.controller;

import com.prueba.ms_proveedores.assemblers.ContratoModelAssembler;
import com.prueba.ms_proveedores.dto.ContratoDTO;
import com.prueba.ms_proveedores.dto.ContratoRequestDTO;
import com.prueba.ms_proveedores.service.ContratoService;
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
@RequestMapping("/api/v2/contratos") // Ruta estandarizada a V2 para soporte HATEOAS
@Slf4j
@RequiredArgsConstructor // Inyección por constructor obligatoria (Punto 1)
@Tag(name = "Contratos V2", description = "API para la gestión y consulta de contratos con soporte de hipermedios (HATEOAS)")
public class ContratoControllerV2 {

    private final ContratoService contratoService;
    private final ContratoModelAssembler assembler;

    @Operation(summary = "Listar todos los contratos (HATEOAS)", description = "Obtiene una lista completa de todos los contratos registrados con enlaces de navegación HATEOAS.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ContratoDTO>>> listarTodos() {
        log.info("/api/v2/contratos - Listar todos (HATEOAS)");
        List<ContratoDTO> contratos = contratoService.listarTodos();

        List<EntityModel<ContratoDTO>> contratoModels = contratos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ContratoDTO>> collectionModel = CollectionModel.of(contratoModels,
                linkTo(methodOn(ContratoControllerV2.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @Operation(summary = "Obtener contrato por ID (HATEOAS)", description = "Busca un registro específico utilizando su ID, inyectando los hipermedios dinámicos correspondientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ID del contrato no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ContratoDTO>> buscarPorId(@PathVariable Integer id) {
        log.info("/api/v2/contratos/{} - Buscar por ID (HATEOAS)", id);
        ContratoDTO dto = contratoService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @Operation(summary = "Crear nuevo contrato", description = "Registra un nuevo contrato en el sistema emitiendo los códigos de estado apropiados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contrato creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<ContratoDTO> crear(@Valid @RequestBody ContratoRequestDTO contratoRequestDTO) {
        log.info("/api/v2/contratos - Crea nuevo registro");
        ContratoDTO creado = contratoService.crear(contratoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar contrato existente", description = "Modifica los valores de un registro de contrato activo buscando por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "El ID del contrato no existe"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ContratoDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ContratoRequestDTO contratoRequestDTO) {
        log.info("/api/v2/contratos/{} - Actualizando datos", id);
        ContratoDTO actualizado = contratoService.actualizar(id, contratoRequestDTO);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar contrato", description = "Elimina un registro de contrato de forma permanente usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro eliminado correctamente (Sin contenido)"),
            @ApiResponse(responseCode = "404", description = "El ID del contrato no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("/api/v2/contratos/{} - Eliminando registro", id);
        contratoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}