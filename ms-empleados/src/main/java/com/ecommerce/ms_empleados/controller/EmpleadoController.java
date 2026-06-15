package com.ecommerce.ms_empleados.controller;

import com.ecommerce.ms_empleados.dto.EmpleadoRequestDTO;
import com.ecommerce.ms_empleados.dto.EmpleadoResponseDTO;
import com.ecommerce.ms_empleados.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados") // URL requerida por la tabla de endpoints
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(empleadoService.findAll()); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(empleadoService.findById(id)); // 200 OK
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(@Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.save(dto)); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(@PathVariable("id") Integer id, @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.ok(empleadoService.update(id, dto)); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}