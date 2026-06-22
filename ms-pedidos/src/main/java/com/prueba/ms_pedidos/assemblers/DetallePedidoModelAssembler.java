package com.prueba.ms_pedidos.assemblers;

import com.prueba.ms_pedidos.controller.DetallePedidoControllerV2;
import com.prueba.ms_pedidos.model.DetallePedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DetallePedidoModelAssembler implements RepresentationModelAssembler<DetallePedido, EntityModel<DetallePedido>> {

    @Override
    public EntityModel<DetallePedido> toModel(DetallePedido detalle) {
        return EntityModel.of(detalle,
                linkTo(methodOn(DetallePedidoControllerV2.class).obtenerPorId(detalle.getId())).withSelfRel(),
                linkTo(methodOn(DetallePedidoControllerV2.class).obtenerTodos()).withRel("detalles-pedido")
        );
    }
}