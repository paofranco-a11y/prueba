package com.prueba.ms_pagos.controller;


import com.prueba.ms_pagos.dto.PagosRequestDTO;
import com.prueba.ms_pagos.dto.PagosResponseDTO;
import com.prueba.ms_pagos.service.PagosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
@Slf4j
public class PagosController {

    private final PagosService pagoService;

    @GetMapping
    public ResponseEntity<List<PagosResponseDTO>> listarTodos() {
        log.info("/api/v1/pagos - Solicitando todos los registros");
        return ResponseEntity.ok(pagoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagosResponseDTO> obtenerPorId(@PathVariable("id") Integer id) {
        log.info("/api/v1/pagos/{} - Buscando pago", id);
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PagosResponseDTO> crear(@Valid @RequestBody PagosRequestDTO dto) {
        log.info("/api/v1/pagos - Creando pago para pedido ID: {}", dto.getPedidoId());
        return new ResponseEntity<>(pagoService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagosResponseDTO> actualizar(
            @PathVariable("id") Integer id,
            @Valid @RequestBody PagosRequestDTO dto) {
        log.info("/api/v1/pagos/{} - Actualizando datos", id);
        return ResponseEntity.ok(pagoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        log.info("/api/v1/pagos/{} - Eliminando registro", id);
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
