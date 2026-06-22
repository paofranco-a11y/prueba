package com.prueba.ms_inventario.assemblers;

import com.prueba.ms_inventario.controller.MovimientoStockController;
import com.prueba.ms_inventario.controller.MovimientoStockControllerV2;
import com.prueba.ms_inventario.dto.MovimientoStockResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovimientoStockModelAssembler implements RepresentationModelAssembler<MovimientoStockResponseDTO, EntityModel<MovimientoStockResponseDTO>> {

    @Override
    public EntityModel<MovimientoStockResponseDTO> toModel(MovimientoStockResponseDTO dto) {
        return convertir(dto);
    }

    public EntityModel<MovimientoStockResponseDTO> convertir(MovimientoStockResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(MovimientoStockControllerV2.class).obtenerPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(MovimientoStockControllerV2.class).obtenerTodos()).withRel("movimientos")
        );
    }
}