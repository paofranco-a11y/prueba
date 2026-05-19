package com.prueba.ms_inventario.mapper;

import com.prueba.ms_inventario.dto.InventarioRequestDTO;
import com.prueba.ms_inventario.dto.InventarioResponseDTO;
import com.prueba.ms_inventario.model.Inventario;
import org.springframework.stereotype.Component;
import com.prueba.ms_inventario.dto.MovimientoStockRequestDTO;
import com.prueba.ms_inventario.dto.MovimientoStockResponseDTO;
import com.prueba.ms_inventario.model.MovimientoStock;
import org.springframework.stereotype.Component;



@Component
public class MovimientoStockMapper {
    public MovimientoStock toEntity(MovimientoStockRequestDTO dto) {
        if (dto == null) return null;

        MovimientoStock entity = new MovimientoStock();
        entity.setTipoMovimiento(dto.getTipoMovimiento());
        entity.setMotivoRazon(dto.getMotivoRazon());
        entity.setCantidadMoviendo(dto.getCantidadMoviendo());
        entity.setAprobado(dto.getAprobado());
        entity.setFechaMovimiento(dto.getFechaMovimiento());

        return entity;
    }

    public MovimientoStockResponseDTO toDTO(MovimientoStock entity) {
        if (entity == null) return null;
        MovimientoStockResponseDTO dto = new MovimientoStockResponseDTO();
        dto.setId(entity.getId());
        dto.setTipoMovimiento(entity.getTipoMovimiento());
        dto.setMotivoRazon(entity.getMotivoRazon());
        dto.setCantidadMoviendo(entity.getCantidadMoviendo());
        dto.setAprobado(entity.getAprobado());
        dto.setFechaMovimiento(entity.getFechaMovimiento());
        if (entity.getInventario() != null) {
            dto.setInventarioId(entity.getInventario().getId());
        }
        return dto;
    }



}
