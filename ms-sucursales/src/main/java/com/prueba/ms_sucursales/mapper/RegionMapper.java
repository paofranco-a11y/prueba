package com.prueba.ms_sucursales.mapper;


import com.prueba.ms_sucursales.dto.RegionDTO;
import com.prueba.ms_sucursales.dto.RegionRequestDTO;
import com.prueba.ms_sucursales.model.Region;

public class RegionMapper {

    public static RegionDTO toDTO(Region region) {
        return new RegionDTO(
                region.getId(),
                region.getNombre(),
                region.getCodigo(),
                region.getDescripcion(),
                region.getNumeroComunas(),
                region.isActivo(),
                region.getFechaCreacion()
        );
    }

    public static Region toEntity(RegionRequestDTO dto) {
        Region region = new Region();
        region.setNombre(dto.getNombre());
        region.setCodigo(dto.getCodigo());
        region.setDescripcion(dto.getDescripcion());
        region.setNumeroComunas(dto.getNumeroComunas());
        region.setActivo(dto.isActivo());
        region.setFechaCreacion(dto.getFechaCreacion());
        return region;
    }
}
