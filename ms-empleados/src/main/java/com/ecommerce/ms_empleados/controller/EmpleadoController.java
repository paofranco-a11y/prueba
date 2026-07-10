package com.ecommerce.ms_empleados.controller;

import com.ecommerce.ms_empleados.dto.EmpleadoRequestDTO;
import com.ecommerce.ms_empleados.dto.EmpleadoResponseDTO;
import com.ecommerce.ms_empleados.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
@Tag(name = "Empleados", description = "API para la gestión, registro y administración de empleados en el sistema")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los empleados", description = "Retorna una lista completa con todos los empleados registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida con éxito")
    })
    public ResponseEntity<List<EmpleadoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un empleado por ID", description = "Busca y retorna la información detallada de un empleado específico usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado con éxito"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorId(
            @Parameter(description = "ID del empleado a consultar", required = true, example = "101")
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(empleadoService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo empleado", description = "Registra un nuevo empleado en el sistema con los datos validados del cuerpo de la petición.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o mal formateados")
    })
    public ResponseEntity<EmpleadoResponseDTO> crear(@Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.save(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado existente", description = "Modifica los datos de un empleado existente identificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EmpleadoResponseDTO> actualizar(
            @Parameter(description = "ID del empleado a modificar", required = true, example = "101")
            @PathVariable("id") Integer id,
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.ok(empleadoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado", description = "Remueve un empleado del sistema mediante su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empleado eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del empleado a eliminar", required = true, example = "101")
            @PathVariable("id") Integer id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}