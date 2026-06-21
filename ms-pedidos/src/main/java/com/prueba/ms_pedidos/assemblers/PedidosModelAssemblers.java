package com.prueba.ms_pedidos.assemblers;

import com.prueba.ms_pedidos.controller.PedidoController;
import com.prueba.ms_pedidos.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidosModelAssemblers implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).buscarPorId(pedido.getId())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listarTodos()).withRel("pedidos")
        );
    }
}
