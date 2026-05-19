package com.prueba.ms_inventario.mapper;


import com.prueba.ms_inventario.dto.InventarioRequestDTO;
import com.prueba.ms_inventario.dto.InventarioResponseDTO;
import com.prueba.ms_inventario.model.Inventario;
import org.springframework.stereotype.Component;

@Component
public class InventarioMapper {
    public InventarioResponseDTO toDTO(Inventario entity) {
        if (entity == null) return null;
        InventarioResponseDTO dto = new InventarioResponseDTO();
        dto.setId(entity.getId());
        dto.setProductoId(entity.getProductoId());
        dto.setUbicacionBodega(entity.getUbicacionBodega());
        dto.setCantidadDisponible(entity.getCantidadDisponible());
        dto.setStockMinimoAlerta(entity.getStockMinimoAlerta());
        dto.setActivo(entity.getActivo());
        dto.setFechaUltimaRevision(entity.getFechaUltimaRevision());
        return dto;
    }

    public Inventario toEntity(InventarioRequestDTO dto) {
        if (dto == null) return null;
        Inventario entity = new Inventario();
        entity.setProductoId(dto.getProductoId());
        entity.setUbicacionBodega(dto.getUbicacionBodega());
        entity.setCantidadDisponible(dto.getCantidadDisponible());
        entity.setStockMinimoAlerta(dto.getStockMinimoAlerta());
        entity.setActivo(dto.getActivo());
        entity.setFechaUltimaRevision(dto.getFechaUltimaRevision());
        return entity;
    }



}
