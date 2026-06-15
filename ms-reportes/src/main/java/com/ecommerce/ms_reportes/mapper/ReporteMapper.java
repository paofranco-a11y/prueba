package com.ecommerce.ms_reportes.mapper;

import com.ecommerce.ms_reportes.dto.ReporteRequestDTO;
import com.ecommerce.ms_reportes.dto.ReporteResponseDTO;
import com.ecommerce.ms_reportes.model.Reporte;

public class ReporteMapper {

    public static Reporte toEntity(ReporteRequestDTO dto) {
        if (dto == null) return null;
        Reporte entity = new Reporte();
        entity.setTitulo(dto.getTitulo());
        entity.setTipoReporte(dto.getTipoReporte());
        entity.setTotalIngresos(dto.getTotalIngresos());
        entity.setTotalRegistros(dto.getTotalRegistros());
        entity.setEsConsolidado(dto.isEsConsolidado());
        entity.setFechaGeneracion(dto.getFechaGeneracion());
        return entity;
    }

    public static ReporteResponseDTO toDTO(Reporte entity) {
        if (entity == null) return null;
        ReporteResponseDTO dto = new ReporteResponseDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setTipoReporte(entity.getTipoReporte());
        dto.setTotalIngresos(entity.getTotalIngresos());
        dto.setTotalRegistros(entity.getTotalRegistros());
        dto.setEsConsolidado(entity.isEsConsolidado());
        dto.setFechaGeneracion(entity.getFechaGeneracion());
        return dto;
    }
}
