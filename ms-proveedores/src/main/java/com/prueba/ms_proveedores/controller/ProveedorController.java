package com.prueba.ms_proveedores.controller;

import com.prueba.ms_proveedores.dto.ProveedorDTO;
import com.prueba.ms_proveedores.dto.ProveedorRequestDTO;
import com.prueba.ms_proveedores.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/proveedores")
    public ResponseEntity<List<ProveedorDTO>> listarTodos() {
        List<ProveedorDTO> proveedores = proveedorService.listarTodos();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/proveedores/{id}")
    public ResponseEntity<ProveedorDTO> buscarPorId(@PathVariable Integer id) {
        ProveedorDTO proveedor = proveedorService.buscarPorId(id);
        return ResponseEntity.ok(proveedor);
    }

    @PostMapping("/proveedores")
    public ResponseEntity<ProveedorDTO> crear(@Valid @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        ProveedorDTO creado = proveedorService.crear(proveedorRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/proveedores/{id}")
    public ResponseEntity<ProveedorDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        ProveedorDTO actualizado = proveedorService.actualizar(id, proveedorRequestDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/proveedores/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/proveedores/activos")
    public ResponseEntity<List<ProveedorDTO>> buscarProveedoresActivos() {
        List<ProveedorDTO> activos = proveedorService.buscarProveedoresActivos();
        return ResponseEntity.ok(activos);
    }
}