package com.ecommerce.ms_empleados.mapper;

import com.ecommerce.ms_empleados.dto.EmpleadoRequestDTO;
import com.ecommerce.ms_empleados.dto.EmpleadoResponseDTO;
import com.ecommerce.ms_empleados.model.Empleado;

public class EmpleadoMapper {

    public static Empleado toEntity(EmpleadoRequestDTO dto) {
        if (dto == null) return null;
        Empleado entity = new Empleado();
        entity.setNombreCompleto(dto.getNombreCompleto());
        entity.setCorreoElectronico(dto.getCorreoElectronico());
        entity.setSueldoBase(dto.getSueldoBase());
        entity.setSucursalId(dto.getSucursalId());
        entity.setEstaActivo(dto.isEstaActivo());
        entity.setFechaIngreso(dto.getFechaIngreso());
        return entity;
    }

    public static EmpleadoResponseDTO toDTO(Empleado entity) {
        if (entity == null) return null;
        EmpleadoResponseDTO dto = new EmpleadoResponseDTO();
        dto.setId(entity.getId());
        dto.setNombreCompleto(entity.getNombreCompleto());
        dto.setCorreoElectronico(entity.getCorreoElectronico());
        dto.setSueldoBase(entity.getSueldoBase());
        dto.setSucursalId(entity.getSucursalId());
        dto.setEstaActivo(entity.isEstaActivo());
        dto.setFechaIngreso(entity.getFechaIngreso());
        return dto;
    }
}
