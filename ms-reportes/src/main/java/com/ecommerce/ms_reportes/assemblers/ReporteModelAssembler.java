package com.ecommerce.ms_reportes.assemblers;

import com.ecommerce.ms_reportes.controller.ReporteControllerV2;
import com.ecommerce.ms_reportes.model.Reporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<Reporte, EntityModel<Reporte>> {

    @Override
    public EntityModel<Reporte> toModel(Reporte reporte) {
        return EntityModel.of(reporte,
                linkTo(methodOn(ReporteControllerV2.class).obtenerPorId(reporte.getId())).withSelfRel(),
                linkTo(methodOn(ReporteControllerV2.class).listarTodos()).withRel("reportes")
        );
    }
}
