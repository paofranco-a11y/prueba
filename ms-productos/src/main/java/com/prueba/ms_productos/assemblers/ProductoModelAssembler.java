package com.prueba.ms_productos.assemblers;

import com.prueba.ms_productos.controller.ProductoController;
import com.prueba.ms_productos.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).obtenerPorId(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listarTodas()).withRel("producto")
        );
    }
}