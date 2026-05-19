package com.prueba.ms_pedidos.controller;

import com.prueba.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.prueba.ms_pedidos.dto.DetallePedidoResponseDTO;
import com.prueba.ms_pedidos.service.DetallePedidoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detalles-pedido")
@Slf4j
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService service;

    @GetMapping
    public ResponseEntity<List<DetallePedidoResponseDTO>> obtenerTodos() {
        log.info("/api/v1/detalles-pedido - Solicitando listado completo");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("/api/v1/detalles-pedido/{} - Buscando registro", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<DetallePedidoResponseDTO> crear(@Valid @RequestBody DetallePedidoRequestDTO dto) {
        log.info("/api/v1/detalles-pedido - Agregando nuevo item");
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DetallePedidoRequestDTO dto) {
        log.info("/api/v1/detalles-pedido/{} - Solicitud de edicion", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("/api/v1/detalles-pedido/{} - Solicitud de eliminacion", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}