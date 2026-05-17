package com.prueba.ms_usuario.controller;

import com.prueba.ms_usuario.dto.PerfilDTO;
import com.prueba.ms_usuario.dto.PerfilRequestDTO;
import com.prueba.ms_usuario.service.PerfilService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/perfiles")
    public ResponseEntity<List<PerfilDTO>> listarPerfiles() {
        return ResponseEntity.ok(perfilService.listarPerfiles());
    }

    @GetMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> obtenerPerfilPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(perfilService.obtenerPerfilPorId(id));
    }

    @PostMapping("/perfiles")
    public ResponseEntity<PerfilDTO> crearPerfil(@Valid @RequestBody PerfilRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(perfilService.crearPerfil(dto));
    }

    @PutMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> actualizarPerfil(
            @PathVariable Integer id,
            @Valid @RequestBody PerfilRequestDTO dto) {
        return ResponseEntity.ok(perfilService.actualizarPerfil(id, dto));
    }

    @DeleteMapping("/perfiles/{id}")
    public ResponseEntity<Void> eliminarPerfil(@PathVariable Integer id) {
        perfilService.eliminarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}