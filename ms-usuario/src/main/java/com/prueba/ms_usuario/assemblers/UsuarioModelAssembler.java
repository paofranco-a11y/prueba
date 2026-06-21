package com.prueba.ms_usuario.assemblers;

import com.prueba.ms_usuario.controller.UsuarioControllerV2;
import com.prueba.ms_usuario.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                // Apuntará al metodo "obtenerUsuarioPorId" de la versión V2
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarioPorId(usuario.getId())).withSelfRel(),
                // Apuntará al metodo "listarUsuarios" de la versión V2
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("usuarios")
        );
    }
}