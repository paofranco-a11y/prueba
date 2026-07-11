package com.prueba.ms_proveedores.assemblers;

import com.prueba.ms_proveedores.controller.ProveedorControllerV2;
import com.prueba.ms_proveedores.dto.ProveedorDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProveedorModelAssembler implements RepresentationModelAssembler<ProveedorDTO, EntityModel<ProveedorDTO>> {

    @Override
    public EntityModel<ProveedorDTO> toModel(ProveedorDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ProveedorControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(ProveedorControllerV2.class).listarTodos()).withRel("proveedores")
        );
    }
}