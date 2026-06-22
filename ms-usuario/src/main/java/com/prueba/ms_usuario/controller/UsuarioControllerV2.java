package com.prueba.ms_usuario.controller;

import com.prueba.ms_usuario.assemblers.UsuarioModelAssembler;
import com.prueba.ms_usuario.dto.UsuarioDTO;
import com.prueba.ms_usuario.service.UsuarioService;
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
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    // Buscar por ID con HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> obtenerUsuarioPorId(@PathVariable Integer id) {
        UsuarioDTO dto = usuarioService.obtenerUsuarioPorId(id);

        // Creamos el modelo con los enlaces dinámicos autodescriptivos
        EntityModel<UsuarioDTO> model = EntityModel.of(dto,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarioPorId(id)).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("usuarios")
        );

        return ResponseEntity.ok(model);
    }

    // Listar todos con HATEOAS
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UsuarioDTO>>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();

        // Convertimos cada objeto de la lista en un modelo individual con enlaces
        List<EntityModel<UsuarioDTO>> usuarioModels = usuarios.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarioPorId(dto.getId())).withSelfRel(), // Asegúrate de que tu DTO tenga .getId()
                        linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("usuarios")
                ))
                .collect(Collectors.toList());

        // Juntamos todo en la colección principal y le agregamos el enlace a sí misma
        CollectionModel<EntityModel<UsuarioDTO>> collectionModel = CollectionModel.of(usuarioModels,
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }
}