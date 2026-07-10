package com.ecommerce.ms_envios.controller;

import com.ecommerce.ms_envios.dto.EnvioRequestDTO;
import com.ecommerce.ms_envios.dto.EnvioResponseDTO;
import com.ecommerce.ms_envios.service.EnvioService;
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
@RequestMapping("/api/v1/envios")
@Tag(name = "Envíos", description = "API para la gestión, despacho y seguimiento logístico de envíos")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los envíos", description = "Retorna una lista con todos los envíos registrados en el sistema de manera global.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de envíos recuperada con éxito")
    })
    public ResponseEntity<List<EnvioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(envioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un envío por ID", description = "Busca y devuelve la información detallada de un envío específico usando su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El envío solicitado no existe en la base de datos")
    })
    public ResponseEntity<EnvioResponseDTO> obtenerPorId(
            @Parameter(description = "ID del envío a consultar", required = true, example = "1")
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(envioService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo envío", description = "Registra un nuevo proceso de envío con los datos provistos en el cuerpo de la petición.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Envío creado y procesado correctamente"),
            @ApiResponse(responseCode = "400", description = "Estructura del JSON de entrada inválida o faltan campos obligatorios")
    })
    public ResponseEntity<EnvioResponseDTO> crear(@Valid @RequestBody EnvioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(envioService.save(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un envío existente", description = "Modifica de manera integral o individual los campos de un envío identificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío modificado de manera exitosa"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada incorrectos o inconsistentes"),
            @ApiResponse(responseCode = "404", description = "No se localizó ningún envío con el ID proporcionado")
    })
    public ResponseEntity<EnvioResponseDTO> actualizar(
            @Parameter(description = "ID del envío a modificar", required = true, example = "1")
            @PathVariable("id") Integer id,
            @Valid @RequestBody EnvioRequestDTO dto) {
        return ResponseEntity.ok(envioService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un envío", description = "Remueve permanentemente el registro de un envío de la base de datos mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Envío eliminado satisfactoriamente del sistema"),
            @ApiResponse(responseCode = "404", description = "El envío a eliminar no fue encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del envío a eliminar", required = true, example = "1")
            @PathVariable("id") Integer id) {
        envioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}