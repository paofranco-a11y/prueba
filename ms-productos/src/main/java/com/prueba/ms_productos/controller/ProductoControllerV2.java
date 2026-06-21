package com.prueba.ms_productos.controller;

import com.prueba.ms_productos.assemblers.ProductoModelAssembler;
import com.prueba.ms_productos.model.Producto;
import com.prueba.ms_productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
@Tag(name = "Productos V2 (HATEOAS)", description = "Gestión de productos con enlaces HATEOAS")
public class ProductoControllerV2 {

    private final ProductoService productoService;
    private final ProductoModelAssembler assembler;

    @GetMapping("/productos")
    @Operation(
            summary = "Listar todos los productos",
            description = "Retorna lista completa de productos con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> listarTodas() {
        log.info("/api/v2/productos - Listando todos los productos");

        List<EntityModel<Producto>> productos = productoService.listarProductosModel()
                .stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Producto>> collection = CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).listarTodas()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/productos/{id}")
    @Operation(
            summary = "Obtener producto por ID",
            description = "Busca un producto por su ID con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<Producto>> obtenerPorId(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Integer id) {
        log.info("/api/v2/productos/{} - Buscando producto", id);

        Producto producto = productoService.obtenerProductoModelPorId(id);
        return ResponseEntity.ok(assembler.toModel(producto));
    }

    @PostMapping("/productos")
    @Operation(
            summary = "Crear producto",
            description = "Registra un nuevo producto con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<EntityModel<Producto>> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del producto a crear"
            )
            @Valid @RequestBody Producto producto) {
        log.info("/api/v2/productos - Creando producto");

        Producto nuevo = productoService.crearProductoModel(producto);
        return new ResponseEntity<>(assembler.toModel(nuevo), HttpStatus.CREATED);
    }

    @PutMapping("/productos/{id}")
    @Operation(
            summary = "Actualizar producto",
            description = "Modifica un producto existente con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<Producto>> actualizar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody Producto producto) {
        log.info("/api/v2/productos/{} - Actualizando producto", id);

        Producto actualizado = productoService.actualizarProductoModel(id, producto);
        return ResponseEntity.ok(assembler.toModel(actualizado));
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
        log.info("/api/v2/productos/{} - Eliminando producto", id);

        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/productos/buscar")
    @Operation(
            summary = "Buscar productos por nombre y precio",
            description = "Retorna productos filtrados con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> buscarPorNombreYPrecio(
            @Parameter(description = "Nombre del producto a buscar", example = "Laptop")
            @RequestParam String nombre,
            @Parameter(description = "Precio máximo del producto", example = "999.99")
            @RequestParam Double precioMaximo) {
        log.info("/api/v2/productos/buscar - nombre: {}, precioMax: {}", nombre, precioMaximo);

        List<EntityModel<Producto>> productos = productoService.buscarPorNombreYPrecioMenorModel(nombre, precioMaximo)
                .stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Producto>> collection = CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).buscarPorNombreYPrecio(nombre, precioMaximo)).withSelfRel());

        return ResponseEntity.ok(collection);
    }
}