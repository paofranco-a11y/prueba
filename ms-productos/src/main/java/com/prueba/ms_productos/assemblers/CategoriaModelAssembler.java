package com.prueba.ms_productos.assemblers;

import com.prueba.ms_productos.controller.CategoriaController;
import com.prueba.ms_productos.model.Categoria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<Categoria, EntityModel<Categoria>> {

    @Override
    public EntityModel<Categoria> toModel(Categoria categoria) {
        return EntityModel.of(categoria,
                linkTo(methodOn(CategoriaController.class).obtenerPorId(categoria.getId())).withSelfRel(),
                linkTo(methodOn(CategoriaController.class).listarTodas()).withRel("categorias")
        );
    }
}