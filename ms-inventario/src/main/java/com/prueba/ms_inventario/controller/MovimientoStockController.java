package com.prueba.ms_inventario.controller;

import com.prueba.ms_inventario.dto.MovimientoStockRequestDTO;
import com.prueba.ms_inventario.dto.MovimientoStockResponseDTO;
import com.prueba.ms_inventario.service.MovimientoStockService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movimientos-stock")
@Slf4j
public class MovimientoStockController {

    @Autowired
    private MovimientoStockService service;

    @GetMapping
    public ResponseEntity<List<MovimientoStockResponseDTO>> obtenerTodos() {
        log.info("/api/v1/movimientos-stock - Listando historial");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoStockResponseDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("/api/v1/movimientos-stock/{} - Buscando registro", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<MovimientoStockResponseDTO> crear(@Valid @RequestBody MovimientoStockRequestDTO dto) {
        log.info("/api/v1/movimientos-stock - Creando nuevo movimiento");
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoStockResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody MovimientoStockRequestDTO dto) {
        log.info("/api/v1/movimientos-stock/{} - Actualizando datos manuales", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("/api/v1/movimientos-stock/{} - Eliminando del historial", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}