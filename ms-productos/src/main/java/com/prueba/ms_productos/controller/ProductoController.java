package com.prueba.ms_productos.controller;

import com.prueba.ms_productos.dto.ProductoDTO;
import com.prueba.ms_productos.dto.ProductoRequestDTO;
import com.prueba.ms_productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Productos", description = "Gestión de productos del e-commerce")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    @Operation(
            summary = "Listar todos los productos",
            description = "Retorna la lista completa de productos registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ProductoDTO>> listarTodas() {
        log.info("Controller: Listando todos los productos");
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/productos/{id}")
    @Operation(
            summary = "Obtener producto por ID",
            description = "Busca y retorna un producto específico por su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ProductoDTO> obtenerPorId(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Integer id) {
        log.info("Controller: Obteniendo producto ID: {}", id);
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @PostMapping("/productos")
    @Operation(
            summary = "Crear producto",
            description = "Registra un nuevo producto en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ProductoDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del producto a crear"
            )
            @Valid @RequestBody ProductoRequestDTO dto) {
        log.info("Controller: Creando producto");
        return new ResponseEntity<>(productoService.crearProducto(dto), HttpStatus.CREATED);
    }

    @PutMapping("/productos/{id}")
    @Operation(
            summary = "Actualizar producto",
            description = "Modifica los datos de un producto existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoDTO> actualizar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody ProductoRequestDTO dto) {
        log.info("Controller: Actualizando producto ID: {}", id);
        return ResponseEntity.ok(productoService.actualizarProducto(id, dto));
    }

    @DeleteMapping("/productos/{id}")
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina permanentemente un producto por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Integer id) {
        log.info("Controller: Eliminando producto ID: {}", id);
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/productos/buscar")
    @Operation(
            summary = "Buscar productos por nombre y precio",
            description = "Retorna productos que coincidan con el nombre y tengan precio menor al indicado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ProductoDTO>> buscarPorNombreYPrecio(
            @Parameter(description = "Nombre del producto a buscar", example = "Laptop")
            @RequestParam String nombre,
            @Parameter(description = "Precio máximo del producto", example = "999.99")
            @RequestParam Double precioMaximo) {
        log.info("Controller: Buscando por nombre '{}' y precio menor a: {}", nombre, precioMaximo);
        List<ProductoDTO> productos = productoService.buscarPorNombreYPrecioMenor(nombre, precioMaximo);
        return ResponseEntity.ok(productos);
    }
}