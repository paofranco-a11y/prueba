package com.ecommerce.ms_envios.assemblers;

import com.prueba.ms_proveedores.controller.ProveedorControllerV2;
import com.prueba.ms_proveedores.dto.ProveedorDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SeguimientoModelAssembler implements RepresentationModelAssembler<SeguimientoDTO, EntityModel<SeguimientoDTO>> {

    @Override
    public EntityModel <SeguimientoDTO> toModel(SeguimientoDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(SeguimientoControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(SeguimientoControllerV2.class).listarTodos()).withRel("proveedores")
        );
    }
}