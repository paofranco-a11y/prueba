package com.prueba.ms_proveedores.controller;

import com.prueba.ms_proveedores.dto.ContratoDTO;
import com.prueba.ms_proveedores.dto.ContratoRequestDTO;
import com.prueba.ms_proveedores.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contratos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @GetMapping
    public ResponseEntity<List<ContratoDTO>> listarTodos() {
        List<ContratoDTO> contratos = contratoService.listarTodos();
        return ResponseEntity.ok(contratos); // Retorna HTTP 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> buscarPorId(@PathVariable Integer id) {
        ContratoDTO contrato = contratoService.buscarPorId(id);
        return ResponseEntity.ok(contrato);
    }


    @PostMapping
    public ResponseEntity<ContratoDTO> crear(@Valid @RequestBody ContratoRequestDTO contratoRequestDTO) {
        ContratoDTO creado = contratoService.crear(contratoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ContratoDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ContratoRequestDTO contratoRequestDTO) {
        ContratoDTO actualizado = contratoService.actualizar(id, contratoRequestDTO);
        return ResponseEntity.ok(actualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        contratoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}