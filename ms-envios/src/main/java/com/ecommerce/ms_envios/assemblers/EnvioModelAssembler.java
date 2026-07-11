package com.ecommerce.ms_envios.assemblers;

import com.ecommerce.ms_envios.controller.EnvioControllerV2;
import com.ecommerce.ms_envios.dto.EnvioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<EnvioResponseDTO, EntityModel<EnvioResponseDTO>> {

    @Override
    public EntityModel<EnvioResponseDTO> toModel(EnvioResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(EnvioControllerV2.class).obtenerPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(EnvioControllerV2.class).listarTodos()).withRel("envios")
        );
    }
}