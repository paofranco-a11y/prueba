package com.prueba.ms_productos.controller;

import com.prueba.ms_productos.dto.CategoriaDTO;
import com.prueba.ms_productos.dto.CategoriaRequestDTO;
import com.prueba.ms_productos.service.CategoriaService;
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
@Tag(name = "Categorias", description = "Gestión de categorías de productos del e-commerce")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    @Operation(
            summary = "Listar todas las categorías",
            description = "Retorna la lista completa de categorías registradas"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<CategoriaDTO>> listarTodas() {
        log.info("Controller: Listando todas las categorias");
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/categorias/{id}")
    @Operation(
            summary = "Obtener categoría por ID",
            description = "Busca y retorna una categoría específica por su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CategoriaDTO> obtenerPorId(
            @Parameter(description = "ID de la categoría", example = "1")
            @PathVariable Integer id) {
        log.info("Controller: Obteniendo categoria ID: {}", id);
        return ResponseEntity.ok(categoriaService.obtenerCategoriaPorId(id));
    }

    @PostMapping("/categorias")
    @Operation(
            summary = "Crear categoría",
            description = "Registra una nueva categoría de productos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CategoriaDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la categoría a crear"
            )
            @Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("Controller: Creando categoria");
        return new ResponseEntity<>(categoriaService.crearCategoria(dto), HttpStatus.CREATED);
    }

    @PutMapping("/categorias/{id}")
    @Operation(
            summary = "Actualizar categoría",
            description = "Modifica los datos de una categoría existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<CategoriaDTO> actualizar(
            @Parameter(description = "ID de la categoría", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("Controller: Actualizando categoria ID: {}", id);
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, dto));
    }

    @DeleteMapping("/categorias/{id}")
    @Operation(
            summary = "Eliminar categoría",
            description = "Elimina permanentemente una categoría por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la categoría", example = "1")
            @PathVariable Integer id) {
        log.info("Controller: Eliminando categoria ID: {}", id);
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}