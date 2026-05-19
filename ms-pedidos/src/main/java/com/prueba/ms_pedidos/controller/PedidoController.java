package com.prueba.ms_pedidos.controller;

import com.prueba.ms_pedidos.dto.PedidoRequestDTO;
import com.prueba.ms_pedidos.dto.PedidoResponseDTO;
import com.prueba.ms_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(@Valid @RequestBody PedidoRequestDTO request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @GetMapping("/pagados")
    public ResponseEntity<List<PedidoResponseDTO>> listarPagados() {
        return ResponseEntity.ok(service.obtenerPagados());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> actualizar(
            @PathVariable Integer id,
            @RequestBody PedidoRequestDTO dto) {
        log.info("Endpoint: PUT /api/v1/pedidos/{} - Actualizacion completa de datos", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }


    // conexion con pagos
    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam String estado) {
        log.info("/api/v1/pedidos/{}/estado - Cambiando estado a {}", id, estado);
        service.actualizarEstado(id, estado);
        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}