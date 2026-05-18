package com.prueba.ms_sucursales.controller;

import com.prueba.ms_sucursales.dto.RegionDTO;
import com.prueba.ms_sucursales.dto.RegionRequestDTO;
import com.prueba.ms_sucursales.service.RegionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/regiones")
    public ResponseEntity<List<RegionDTO>> listarTodas() {
        log.info("Controller: Listando todas las regiones");
        return ResponseEntity.ok(regionService.listarRegiones());
    }

    @GetMapping("/regiones/{id}")
    public ResponseEntity<RegionDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("Controller: Obteniendo region ID: {}", id);
        return ResponseEntity.ok(regionService.obtenerRegionPorId(id));
    }

    @PostMapping("/regiones")
    public ResponseEntity<RegionDTO> crear(@Valid @RequestBody RegionRequestDTO dto) {
        log.info("Controller: Creando region");
        return new ResponseEntity<>(regionService.crearRegion(dto), HttpStatus.CREATED);
    }

    @PutMapping("/regiones/{id}")
    public ResponseEntity<RegionDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody RegionRequestDTO dto) {
        log.info("Controller: Actualizando region ID: {}", id);
        return ResponseEntity.ok(regionService.actualizarRegion(id, dto));
    }

    @DeleteMapping("/regiones/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Controller: Eliminando region ID: {}", id);
        regionService.eliminarRegion(id);
        return ResponseEntity.noContent().build();
    }
}