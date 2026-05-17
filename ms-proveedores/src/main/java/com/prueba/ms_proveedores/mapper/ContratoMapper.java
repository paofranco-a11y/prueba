package com.prueba.ms_proveedores.mapper;

import com.prueba.ms_proveedores.dto.ContratoDTO;
import com.prueba.ms_proveedores.dto.ContratoRequestDTO;
import com.prueba.ms_proveedores.model.Contrato;
import com.prueba.ms_proveedores.model.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class ContratoMapper {

    public ContratoDTO toDTO(Contrato entity) {
        if (entity == null) return null;
        ContratoDTO dto = new ContratoDTO();
        dto.setId(entity.getId());
        dto.setCodigoContrato(entity.getCodigoContrato());
        dto.setMontoTotal(entity.getMontoTotal());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaTermino(entity.getFechaTermino());
        dto.setVigente(entity.isVigente());
        if (entity.getProveedor() != null) {
            dto.setProveedorId(entity.getProveedor().getId());
        }
        return dto;
    }

    public Contrato toEntity(ContratoRequestDTO dto, Proveedor proveedor) {
        if (dto == null) return null;
        Contrato entity = new Contrato();
        entity.setCodigoContrato(dto.getCodigoContrato());
        entity.setMontoTotal(dto.getMontoTotal());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaTermino(dto.getFechaTermino());
        entity.setVigente(dto.isVigente());
        entity.setProveedor(proveedor); // Vincula al proveedor padre
        return entity;
    }
}