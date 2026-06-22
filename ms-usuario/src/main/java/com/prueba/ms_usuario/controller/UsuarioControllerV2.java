package com.prueba.ms_usuario.controller;

import com.prueba.ms_usuario.assemblers.UsuarioModelAssembler;
import com.prueba.ms_usuario.dto.UsuarioDTO;
import com.prueba.ms_usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/usuarios") // Ruta V2 para HATEOAS
@Slf4j
@Tag(name = "Usuarios V2", description = "API para la gestión y consulta de usuarios con soporte de hipermedios (HATEOAS)")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    // Buscar por ID con HATEOAS
    @Operation(summary = "Obtener inventario por ID", description = "Busca un registro específico de usuario utilizando su ID, incluyendo enlaces HATEOAS.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ID del usuario no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> obtenerUsuarioPorId(@PathVariable Integer id) {
        log.info("/api/v2/usuarios/{} - Buscar por ID (HATEOAS)", id);
        UsuarioDTO dto = usuarioService.obtenerUsuarioPorId(id);

        // Creamos el modelo con los enlaces dinámicos autodescriptivos
        EntityModel<UsuarioDTO> model = EntityModel.of(dto,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarioPorId(id)).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("usuarios")
        );

        return ResponseEntity.ok(model);
    }

    // Listar todos con HATEOAS
    @Operation(summary = "Listar todo el inventario", description = "Obtiene una lista completa de todos los registros de usuarios con enlaces de navegación HATEOAS.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UsuarioDTO>>> listarUsuarios() {
        log.info("/api/v2/usuarios - Listar todos (HATEOAS)");
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();

        // Convertimos cada objeto de la lista en un modelo individual con enlaces
        List<EntityModel<UsuarioDTO>> usuarioModels = usuarios.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarioPorId(dto.getId())).withSelfRel(),
                        linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("usuarios")
                ))
                .collect(Collectors.toList());

        // Juntamos todo en la colección principal y le agregamos el enlace a sí misma
        CollectionModel<EntityModel<UsuarioDTO>> collectionModel = CollectionModel.of(usuarioModels,
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }
}