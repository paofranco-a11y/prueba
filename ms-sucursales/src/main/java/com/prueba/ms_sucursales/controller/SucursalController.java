package com.prueba.ms_sucursales.controller;

import com.prueba.ms_sucursales.dto.SucursalDTO;
import com.prueba.ms_sucursales.dto.SucursalRequestDTO;
import com.prueba.ms_sucursales.service.SucursalService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> listarTodas() {
        log.info("Controller: Listando todas las sucursales");
        return ResponseEntity.ok(sucursalService.listarSucursales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("Controller: Obteniendo sucursal ID: {}", id);
        return ResponseEntity.ok(sucursalService.obtenerSucursalPorId(id));
    }

    @PostMapping
    public ResponseEntity<SucursalDTO> crear(@Valid @RequestBody SucursalRequestDTO dto) {
        log.info("Controller: Creando sucursal");
        return new ResponseEntity<>(sucursalService.crearSucursal(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody SucursalRequestDTO dto) {
        log.info("Controller: Actualizando sucursal ID: {}", id);
        return ResponseEntity.ok(sucursalService.actualizarSucursal(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Controller: Eliminando sucursal ID: {}", id);
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<SucursalDTO>> buscarPorRegion(@RequestParam String nombreRegion) {
        log.info("Controller: Buscando por region: {}", nombreRegion);
        List<SucursalDTO> sucursales = sucursalService.buscarPorNombreRegion(nombreRegion);
        return ResponseEntity.ok(sucursales);
    }
}