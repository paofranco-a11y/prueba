package com.prueba.ms_usuario.controller;

import com.prueba.ms_usuario.dto.UsuarioDTO;
import com.prueba.ms_usuario.dto.UsuarioRequestDTO;
import com.prueba.ms_usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Slf4j
@Tag(name = "Usuarios", description = "API para la gestión y consulta de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todo el inventario", description = "Obtiene una lista completa de todos los registros de usuarios.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        log.info("/api/v1/usuarios - Listar todos");
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @Operation(summary = "Obtener inventario por ID", description = "Busca un registro específico de usuario utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ID del usuario no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Integer id) {
        log.info("/api/v1/usuarios/{} - Buscar por ID", id);
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @Operation(summary = "Crear nuevo registro de inventario", description = "Registra un nuevo usuario con sus datos iniciales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("/api/v1/usuarios - Crea nuevo registro");
        UsuarioDTO creado = usuarioService.crearUsuario(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar registro de inventario", description = "Actualiza los datos de un registro de usuario existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "El ID del usuario no existe"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("/api/v1/usuarios/{} - Actualizando datos", id);
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

    @Operation(summary = "Eliminar registro de inventario", description = "Elimina un registro de usuario de forma permanente usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro eliminado correctamente (Sin contenido)"),
            @ApiResponse(responseCode = "404", description = "El ID del usuario no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        log.info("/api/v1/usuarios/{} - Eliminando registro", id);
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar usuario por email", description = "Busca registros de usuarios filtrando de manera exacta por su dirección de correo electrónico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda procesada correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de búsqueda inválidos")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioDTO>> buscarPorEmail(@RequestParam String email) {
        log.info("/api/v1/usuarios/buscar - Buscando por email: {}", email);
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }
}