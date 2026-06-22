package com.prueba.ms_usuario.controller;

import com.prueba.ms_usuario.assemblers.PerfilModelAssembler;
import com.prueba.ms_usuario.dto.PerfilDTO;
import com.prueba.ms_usuario.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/perfiles") // Ruta V2 para HATEOAS
public class PerfilControllerV2 {

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private PerfilModelAssembler assembler;

    // Buscar por ID con HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PerfilDTO>> obtenerPerfilPorId(@PathVariable Integer id) {
        PerfilDTO dto = perfilService.obtenerPerfilPorId(id);

        EntityModel<PerfilDTO> model = EntityModel.of(dto,
                linkTo(methodOn(PerfilControllerV2.class).obtenerPerfilPorId(id)).withSelfRel(),
                linkTo(methodOn(PerfilControllerV2.class).listarPerfiles()).withRel("perfiles")
        );

        return ResponseEntity.ok(model);
    }

    // Listar todos con HATEOAS
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PerfilDTO>>> listarPerfiles() {
        List<PerfilDTO> perfiles = perfilService.listarPerfiles();

        List<EntityModel<PerfilDTO>> perfilModels = perfiles.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(PerfilControllerV2.class).obtenerPerfilPorId(dto.getId())).withSelfRel(), // Asegúrate de que tu DTO tenga .getId()
                        linkTo(methodOn(PerfilControllerV2.class).listarPerfiles()).withRel("perfiles")
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<PerfilDTO>> collectionModel = CollectionModel.of(perfilModels,
                linkTo(methodOn(PerfilControllerV2.class).listarPerfiles()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }
}