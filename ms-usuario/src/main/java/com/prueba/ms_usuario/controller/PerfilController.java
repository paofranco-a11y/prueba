package com.prueba.ms_usuario.controller;

import com.prueba.ms_usuario.dto.PerfilDTO;
import com.prueba.ms_usuario.dto.PerfilRequestDTO;
import com.prueba.ms_usuario.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Controlador de Perfiles (V1)", description = "Endpoints para la gestión del ciclo de vida y CRUD de los perfiles de usuario")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @Operation(summary = "Listar todos los perfiles", description = "Recupera una lista completa con todos los perfiles almacenados en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa. Devuelve la lista de perfiles."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al procesar la solicitud.", content = @Content)
    })
    @GetMapping("/perfiles")
    public ResponseEntity<List<PerfilDTO>> listarPerfiles() {
        return ResponseEntity.ok(perfilService.listarPerfiles());
    }

    @Operation(summary = "Obtener un perfil por ID", description = "Busca un perfil específico utilizando su identificador numérico único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado exitosamente."),
            @ApiResponse(responseCode = "404", description = "El perfil con el ID proporcionado no existe.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
    })
    @GetMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> obtenerPerfilPorId(
            @Parameter(description = "ID numérico del perfil a buscar", required = true, example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(perfilService.obtenerPerfilPorId(id));
    }

    @Operation(summary = "Crear un nuevo perfil", description = "Valida la existencia del usuario asociado y registra un nuevo perfil en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perfil creado y vinculado de manera exitosa."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o faltantes en el JSON.", content = @Content),
            @ApiResponse(responseCode = "404", description = "El usuarioId especificado no existe en la base de datos.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno al intentar guardar el registro.", content = @Content)
    })
    @PostMapping("/perfiles")
    public ResponseEntity<PerfilDTO> crearPerfil(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Estructura JSON necesaria para registrar un perfil",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PerfilRequestDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"usuarioId\": 1, \"tipoPerfil\": \"Administrador\", \"direccion\": \"Av. Concha y Toro 345, Puente Alto\", \"descripcion\": \"Perfil de TI encargado del mantenimiento del microservicio\", \"verificado\": true, \"fechaCreacion\": \"2026-06-21T00:00:00\"}"
                            )
                    )
            )
            @Valid @RequestBody PerfilRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(perfilService.crearPerfil(dto));
    }

    @Operation(summary = "Actualizar un perfil existente", description = "Localiza el perfil por su ID y actualiza todas sus propiedades basándose en el DTO de entrada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil actualizado correctamente de forma persistente."),
            @ApiResponse(responseCode = "400", description = "Estructura del cuerpo de la petición inválida.", content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontró el Perfil con el ID provisto o el Usuario no existe.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error del sistema al procesar la actualización.", content = @Content)
    })
    @PutMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> actualizarPerfil(
            @Parameter(description = "ID del perfil que se desea modificar", required = true, example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del perfil",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PerfilRequestDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"usuarioId\": 1, \"tipoPerfil\": \"Premium\", \"direccion\": \"Nueva dirección 789, Santiago\", \"descripcion\": \"Perfil actualizado a cliente premium\", \"verificado\": true, \"fechaCreacion\": \"2026-06-21T00:00:00\"}"
                            )
                    )
            )
            @Valid @RequestBody PerfilRequestDTO dto) {
        return ResponseEntity.ok(perfilService.actualizarPerfil(id, dto));
    }

    @Operation(summary = "Eliminar un perfil", description = "Elimina permanentemente un registro de perfil de la base de datos a través de su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil removido de forma satisfactoria. No se devuelve contenido."),
            @ApiResponse(responseCode = "404", description = "El perfil solicitado para eliminación no existe.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
    })
    @DeleteMapping("/perfiles/{id}")
    public ResponseEntity<Void> eliminarPerfil(
            @Parameter(description = "ID del perfil que se va a eliminar", required = true, example = "1")
            @PathVariable Integer id) {
        perfilService.eliminarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}