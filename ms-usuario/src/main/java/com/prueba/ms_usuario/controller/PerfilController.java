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

// Le dice a Spring que esta clase va a recibir las peticiones web (peticiones HTTP)
@RestController
// Define que todas las URLs de este archivo van a empezar con /api/v1
@RequestMapping("/api/v1")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    // Con GET en /perfiles, responde la lista de todos los perfiles de la base de datos
    @GetMapping("/perfiles")
    public ResponseEntity<List<PerfilDTO>> listarPerfiles() {
        return ResponseEntity.ok(perfilService.listarPerfiles());
    }


    @GetMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> obtenerPerfilPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(perfilService.obtenerPerfilPorId(id));
    }

    // Con POST en /perfiles, recibe los datos, los valida en base a las reglas del DTO y crea el perfil nuevo
    @PostMapping("/perfiles")
    public ResponseEntity<PerfilDTO> crearPerfil(@Valid @RequestBody PerfilRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(perfilService.crearPerfil(dto));
    }

    // Con PUT y el ID en la URL, recibe los nuevos datos validados y modifica el perfil que ya existe
    @PutMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> actualizarPerfil(
            @PathVariable Integer id,
            @Valid @RequestBody PerfilRequestDTO dto) {
        return ResponseEntity.ok(perfilService.actualizarPerfil(id, dto));
    }

    // Con DELETE y el ID en la URL, borra el perfil y responde confirmando que ya no hay contenido
    @DeleteMapping("/perfiles/{id}")
    public ResponseEntity<Void> eliminarPerfil(@PathVariable Integer id) {
        perfilService.eliminarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}