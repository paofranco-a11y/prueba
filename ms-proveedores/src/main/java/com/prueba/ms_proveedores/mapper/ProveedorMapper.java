package com.prueba.ms_proveedores.mapper;

import com.prueba.ms_proveedores.dto.ProveedorDTO;
import com.prueba.ms_proveedores.dto.ProveedorRequestDTO;
import com.prueba.ms_proveedores.model.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {

    public ProveedorDTO toDTO(Proveedor entity) {
        if (entity == null) return null;
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setRut(entity.getRut());
        dto.setCalificacion(entity.getCalificacion());
        dto.setActivo(entity.isActivo());
        dto.setContactoEmail(entity.getContactoEmail());
        return dto;
    }

    public Proveedor toEntity(ProveedorRequestDTO dto) {
        if (dto == null) return null;
        Proveedor entity = new Proveedor();
        entity.setNombre(dto.getNombre());
        entity.setRut(dto.getRut());
        entity.setCalificacion(dto.getCalificacion());
        entity.setActivo(dto.isActivo());
        entity.setContactoEmail(dto.getContactoEmail());
        return entity;
    }
}