package com.prueba.ms_usuario.assemblers;

import com.prueba.ms_usuario.controller.PerfilControllerV2;
import com.prueba.ms_usuario.model.Perfil;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PerfilModelAssembler implements RepresentationModelAssembler<Perfil, EntityModel<Perfil>> {

    @Override
    public EntityModel<Perfil> toModel(Perfil perfil) {
        return EntityModel.of(perfil,
                // Apuntará al método "obtenerPerfilPorId" de la versión V2
                linkTo(methodOn(PerfilControllerV2.class).obtenerPerfilPorId(perfil.getId())).withSelfRel(),
                // Apuntará al método "listarPerfiles" de la versión V2
                linkTo(methodOn(PerfilControllerV2.class).listarPerfiles()).withRel("perfiles")
        );
    }
}