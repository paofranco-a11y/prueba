package com.prueba.ms_sucursales.controller;

import com.prueba.ms_sucursales.dto.RegionDTO;
import com.prueba.ms_sucursales.dto.RegionRequestDTO;
import com.prueba.ms_sucursales.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/regiones")
    public ResponseEntity<List<RegionDTO>> listarRegiones() {
        return ResponseEntity.ok(
                regionService.listarRegiones()
        );
    }

    @GetMapping("/regiones/{id}")
    public ResponseEntity<RegionDTO> obtenerRegionPorId(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
                regionService.obtenerRegionPorId(id)
        );
    }

    @PostMapping("/regiones")
    public ResponseEntity<RegionDTO> crearRegion(
            @Valid @RequestBody RegionRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(regionService.crearRegion(dto));
    }

    @PutMapping("/regiones/{id}")
    public ResponseEntity<RegionDTO> actualizarRegion(
            @PathVariable Integer id,
            @Valid @RequestBody RegionRequestDTO dto) {
        return ResponseEntity.ok(regionService.actualizarRegion(id, dto));
    }

    @DeleteMapping("/regiones/{id}")
    public ResponseEntity<Void> eliminarRegion(@PathVariable Integer id) {
        regionService.eliminarRegion(id);
        return ResponseEntity.noContent().build();
    }
}