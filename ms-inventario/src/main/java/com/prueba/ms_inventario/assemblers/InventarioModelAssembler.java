package com.prueba.ms_inventario.assemblers;

import com.prueba.ms_inventario.controller.InventarioControllerV2;
import com.prueba.ms_inventario.dto.InventarioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<InventarioResponseDTO, EntityModel<InventarioResponseDTO>> {

    @Override
    public EntityModel<InventarioResponseDTO> toModel(InventarioResponseDTO dto) {
        return convertir(dto);
    }

    // Este es el metodo al que ControllerV2 llama para ensamblar los links Con este metodo se realiaza
    public EntityModel<InventarioResponseDTO> convertir(InventarioResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(InventarioControllerV2.class).obtenerPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(InventarioControllerV2.class).listarTodos()).withRel("inventarios")
        );
    }
}