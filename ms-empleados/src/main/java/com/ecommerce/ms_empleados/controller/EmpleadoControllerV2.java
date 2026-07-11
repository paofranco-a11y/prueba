package com.ecommerce.ms_empleados.controller;

import com.ecommerce.ms_empleados.assemblers.EmpleadoModelAssembler;
import com.ecommerce.ms_empleados.dto.EmpleadoRequestDTO;
import com.ecommerce.ms_empleados.model.Empleado;
import com.ecommerce.ms_empleados.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/empleados")
@Tag(name = "Empleados V2 - HATEOAS", description = "Endpoints de empleados adaptados al modelo Hypermedia (REST Maturity Level 3)")
public class EmpleadoControllerV2 {

    private final EmpleadoService empleadoService;
    private final EmpleadoModelAssembler assembler;

    public EmpleadoControllerV2(EmpleadoService empleadoService, EmpleadoModelAssembler assembler) {
        this.empleadoService = empleadoService;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Listar todos los empleados con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colección Hypermedia obtenida con éxito")
    })
    public ResponseEntity<CollectionModel<EntityModel<Empleado>>> listarTodos() {
        List<Empleado> empleados = empleadoService.findAllEntities();

        List<EntityModel<Empleado>> empleadoModels = empleados.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Empleado>> collectionModel = CollectionModel.of(empleadoModels,
                linkTo(methodOn(EmpleadoControllerV2.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un empleado por ID con hipervínculos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado y enriquecido con HATEOAS"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado en el sistema")
    })
    public ResponseEntity<EntityModel<Empleado>> obtenerPorId(
            @Parameter(description = "ID único del empleado", required = true, example = "105")
            @PathVariable("id") Integer id) {

        Empleado empleado = empleadoService.findEntityById(id);
        return ResponseEntity.ok(assembler.toModel(empleado));
    }

    @PostMapping
    @Operation(summary = "Crear un empleado en versión V2 (HATEOAS)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Estructura de la petición inválida o error en servicio externo de Sucursales")
    })
    public ResponseEntity<EntityModel<Empleado>> crear(@Valid @RequestBody EmpleadoRequestDTO dto) {
        Empleado nuevoEmpleado = empleadoService.saveEntity(dto);
        EntityModel<Empleado> entityModel = assembler.toModel(nuevoEmpleado);

        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar campos de un empleado (HATEOAS)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado modificado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada incorrectos"),
            @ApiResponse(responseCode = "404", description = "El empleado solicitado no existe")
    })
    public ResponseEntity<EntityModel<Empleado>> actualizar(
            @Parameter(description = "ID del empleado a modificar", required = true, example = "105")
            @PathVariable("id") Integer id,
            @Valid @RequestBody EmpleadoRequestDTO dto) {

        Empleado empleadoActualizado = empleadoService.updateEntity(id, dto);
        return ResponseEntity.ok(assembler.toModel(empleadoActualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empleado eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Empleado inviable o no localizado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del empleado a remover", required = true, example = "105")
            @PathVariable("id") Integer id) {

        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
