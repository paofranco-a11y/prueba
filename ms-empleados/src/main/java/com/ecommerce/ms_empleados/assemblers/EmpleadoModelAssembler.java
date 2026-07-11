package com.ecommerce.ms_empleados.assemblers;

import com.ecommerce.ms_empleados.controller.EmpleadoControllerV2;
import com.ecommerce.ms_empleados.model.Empleado;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<Empleado, EntityModel<Empleado>> {

    @Override
    public EntityModel<Empleado> toModel(Empleado empleado) {
        return EntityModel.of(empleado,
                linkTo(methodOn(EmpleadoControllerV2.class).obtenerPorId(empleado.getId())).withSelfRel(),
                linkTo(methodOn(EmpleadoControllerV2.class).listarTodos()).withRel("empleados")
        );
    }
}
