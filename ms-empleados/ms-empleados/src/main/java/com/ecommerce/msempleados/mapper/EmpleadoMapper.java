package com.ecommerce.msempleados.mapper;

import com.ecommerce.msempleados.dto.EmpleadoDTO;
import com.ecommerce.msempleados.dto.EmpleadoRequestDTO;
import com.ecommerce.msempleados.model.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {

    public EmpleadoDTO toDTO(Empleado entity) {
        if (entity == null) return null;

        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(entity.getId());
        dto.setNombreCompleto(entity.getNombreCompleto());
        dto.setCargo(entity.getCargo());
        dto.setEmailCorporativo(entity.getEmailCorporativo());
        dto.setSueldoBase(entity.getSueldoBase());
        dto.setAniosExperiencia(entity.getAniosExperiencia());
        dto.setActivo(entity.isActivo());
        dto.setFechaIngreso(entity.getFechaIngreso());
        dto.setSucursalId(entity.getSucursalId());

        return dto;
    }

    public Empleado toEntity(EmpleadoRequestDTO dto) {
        if (dto == null) return null;

        Empleado entity = new Empleado();
        entity.setNombreCompleto(dto.getNombreCompleto());
        entity.setCargo(dto.getCargo());
        entity.setEmailCorporativo(dto.getEmailCorporativo());
        entity.setSueldoBase(dto.getSueldoBase());
        entity.setAniosExperiencia(dto.getAniosExperiencia());
        entity.setActivo(dto.isActivo());
        entity.setFechaIngreso(dto.getFechaIngreso());
        entity.setSucursalId(dto.getSucursalId());

        return entity;
    }
}
