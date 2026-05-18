package com.prueba.ms_productos.controller;

import com.prueba.ms_productos.dto.ProductoDTO;
import com.prueba.ms_productos.dto.ProductoRequestDTO;
import com.prueba.ms_productos.service.ProductoService;
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
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> listarTodas() {
        log.info("Controller: Listando todos los productos");
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("Controller: Obteniendo producto ID: {}", id);
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @PostMapping("/productos")
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        log.info("Controller: Creando producto");
        return new ResponseEntity<>(productoService.crearProducto(dto), HttpStatus.CREATED);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoRequestDTO dto) {
        log.info("Controller: Actualizando producto ID: {}", id);
        return ResponseEntity.ok(productoService.actualizarProducto(id, dto));
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Controller: Eliminando producto ID: {}", id);
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/productos/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombreYPrecio(
            @RequestParam String nombre,
            @RequestParam Double precioMaximo) {
        log.info("Controller: Buscando por nombre '{}' y precio menor a: {}", nombre, precioMaximo);
        List<ProductoDTO> productos = productoService.buscarPorNombreYPrecioMenor(nombre, precioMaximo);
        return ResponseEntity.ok(productos);
    }
}