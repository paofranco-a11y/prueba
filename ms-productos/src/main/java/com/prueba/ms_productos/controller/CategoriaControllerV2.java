package com.prueba.ms_productos.controller;

import com.prueba.ms_productos.assemblers.CategoriaModelAssembler;
import com.prueba.ms_productos.model.Categoria;
import com.prueba.ms_productos.service.CategoriaService;
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
@Tag(name = "Categorias V2 (HATEOAS)", description = "Gestión de categorías con enlaces HATEOAS")
public class CategoriaControllerV2 {

    private final CategoriaService categoriaService;
    private final CategoriaModelAssembler assembler;

    @GetMapping("/categorias")
    @Operation(
            summary = "Listar todas las categorías",
            description = "Retorna lista completa de categorías con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CollectionModel<EntityModel<Categoria>>> listarTodas() {
        log.info("/api/v2/categorias - Listando todas las categorias");

        List<EntityModel<Categoria>> categorias = categoriaService.listarCategoriasModel()
                .stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Categoria>> collection = CollectionModel.of(categorias,
                linkTo(methodOn(CategoriaControllerV2.class).listarTodas()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/categorias/{id}")
    @Operation(
            summary = "Obtener categoría por ID",
            description = "Busca una categoría por su ID con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<EntityModel<Categoria>> obtenerPorId(
            @Parameter(description = "ID de la categoría", example = "1")
            @PathVariable Integer id) {
        log.info("/api/v2/categorias/{} - Buscando categoria", id);

        Categoria categoria = categoriaService.obtenerCategoriaModelPorId(id);
        return ResponseEntity.ok(assembler.toModel(categoria));
    }

    @PostMapping("/categorias")
    @Operation(
            summary = "Crear categoría",
            description = "Registra una nueva categoría con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<EntityModel<Categoria>> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la categoría a crear"
            )
            @Valid @RequestBody Categoria categoria) {
        log.info("/api/v2/categorias - Creando categoria");

        Categoria nueva = categoriaService.crearCategoriaModel(categoria);
        return new ResponseEntity<>(assembler.toModel(nueva), HttpStatus.CREATED);
    }

    @PutMapping("/categorias/{id}")
    @Operation(
            summary = "Actualizar categoría",
            description = "Modifica una categoría existente con enlaces HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<EntityModel<Categoria>> actualizar(
            @Parameter(description = "ID de la categoría", example = "1")
            @PathVariable Integer id,
            @Valid @RequestBody Categoria categoria) {
        log.info("/api/v2/categorias/{} - Actualizando categoria", id);

        Categoria actualizada = categoriaService.actualizarCategoriaModel(id, categoria);
        return ResponseEntity.ok(assembler.toModel(actualizada));
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
        log.info("/api/v2/categorias/{} - Eliminando categoria", id);

        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
