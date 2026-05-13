package com.prueba.ms_usuario.controller;

import com.prueba.ms_usuario.dto.UsuarioDTO;
import com.prueba.ms_usuario.dto.UsuarioRequestDTO;
import com.prueba.ms_usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {

        return ResponseEntity.ok(
                usuarioService.listarUsuarios()
        );
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                usuarioService.obtenerUsuarioPorId(id)
        );
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> crearUsuario(
            @Valid @RequestBody UsuarioRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(dto));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioRequestDTO dto) {

        return ResponseEntity.ok(
                usuarioService.actualizarUsuario(id, dto)
        );
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @PathVariable Integer id) {

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios/buscar")
    public ResponseEntity<List<UsuarioDTO>> buscarPorEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(
                usuarioService.buscarPorEmail(email)
        );
    }
}