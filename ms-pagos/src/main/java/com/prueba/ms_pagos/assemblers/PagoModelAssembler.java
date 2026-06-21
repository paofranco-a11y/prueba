package com.prueba.ms_pagos.assemblers;

import com.prueba.ms_pagos.controller.PagosController;
import com.prueba.ms_pagos.model.Pagos;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pagos, EntityModel<Pagos>> {

    @Override
    public EntityModel<Pagos> toModel(Pagos pagos) {
        return EntityModel.of(pagos,
                linkTo(methodOn(PagosController.class).obtenerPorId(pagos.getId())).withSelfRel(),
                linkTo(methodOn(PagosController.class).listarTodos()).withRel("pagos")
        );
    }
}