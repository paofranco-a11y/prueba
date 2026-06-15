package com.ecommerce.ms_envios.controller;

import com.ecommerce.ms_envios.dto.EnvioRequestDTO;
import com.ecommerce.ms_envios.dto.EnvioResponseDTO;
import com.ecommerce.ms_envios.service.EnvioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping
    public ResponseEntity<List<EnvioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(envioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioResponseDTO> obtenerPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(envioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EnvioResponseDTO> crear(@Valid @RequestBody EnvioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(envioService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvioResponseDTO> actualizar(@PathVariable("id") Integer id, @Valid @RequestBody EnvioRequestDTO dto) {
        return ResponseEntity.ok(envioService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        envioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
