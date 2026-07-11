package com.prueba.ms_proveedores.assemblers;

import com.prueba.ms_proveedores.controller.ContratoControllerV2;
import com.prueba.ms_proveedores.dto.ContratoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ContratoModelAssembler implements RepresentationModelAssembler<ContratoDTO, EntityModel<ContratoDTO>> {

    @Override
    public EntityModel<ContratoDTO> toModel(ContratoDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ContratoControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(ContratoControllerV2.class).listarTodos()).withRel("contratos")
        );
    }
}