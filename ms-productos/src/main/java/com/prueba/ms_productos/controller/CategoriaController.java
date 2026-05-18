package com.prueba.ms_productos.controller;

import com.prueba.ms_productos.dto.CategoriaDTO;
import com.prueba.ms_productos.dto.CategoriaRequestDTO;
import com.prueba.ms_productos.service.CategoriaService;
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
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> listarTodas() {
        log.info("Controller: Listando todas las categorias");
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("Controller: Obteniendo categoria ID: {}", id);
        return ResponseEntity.ok(categoriaService.obtenerCategoriaPorId(id));
    }

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaDTO> crear(@Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("Controller: Creando categoria");
        return new ResponseEntity<>(categoriaService.crearCategoria(dto), HttpStatus.CREATED);
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("Controller: Actualizando categoria ID: {}", id);
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, dto));
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Controller: Eliminando categoria ID: {}", id);
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}