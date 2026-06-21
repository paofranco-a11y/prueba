package com.prueba.ms_usuario.controller;

import com.prueba.ms_usuario.dto.UsuarioDTO;
import com.prueba.ms_usuario.dto.UsuarioRequestDTO;
import com.prueba.ms_usuario.service.UsuarioService;
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
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Con GET en /usuarios, responde la lista de todos los usuarios de la base de datos
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // Con GET y pasándole un ID en la URL, busca y devuelve un solo usuario específico
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    // Con POST en /usuarios, recibe los datos, los valida con las reglas del DTO y guarda al usuario nuevo
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(dto));
    }

    // Con PUT y el ID en la URL, recibe los datos nuevos ya validados y edita al usuario existente
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

    // Con DELETE y el ID en la URL, elimina al usuario de la base de datos y avisa que se procesó correctamente
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Con GET en /usuarios/buscar, recibe un correo como parámetro (?email=...) y filtra la lista de usuarios
    @GetMapping("/usuarios/buscar")
    public ResponseEntity<List<UsuarioDTO>> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }
}