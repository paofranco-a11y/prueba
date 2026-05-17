package com.prueba.ms_inventario.controller;


import com.prueba.ms_inventario.dto.InventarioRequestDTO;
import com.prueba.ms_inventario.dto.InventarioResponseDTO;
import com.prueba.ms_inventario.model.Inventario;
import com.prueba.ms_inventario.service.InventarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")
@Slf4j

public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> listarTodo() {
        log.info("/api/v1/inventario - Listar todos");
        return ResponseEntity.ok(inventarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("/api/v1/inventario/{} - Buscar por ID", id);
        return ResponseEntity.ok(inventarioService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<InventarioResponseDTO> guardar(@Valid @RequestBody InventarioRequestDTO dto) {
        log.info("/api/v1/inventario - Crea nuevo registro");
        InventarioResponseDTO creado = inventarioService.crear(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> actualizar(
            @PathVariable("id") Integer id,
            @Valid @RequestBody InventarioRequestDTO dto) {
        log.info("/api/v1/inventario/{} - Actualizando datos", id);
        return ResponseEntity.ok(inventarioService.actualizar(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
