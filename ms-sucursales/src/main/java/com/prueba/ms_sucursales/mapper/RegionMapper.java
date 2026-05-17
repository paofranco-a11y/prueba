package com.prueba.ms_sucursales.mapper;

import com.prueba.ms_sucursales.dto.RegionDTO;
import com.prueba.ms_sucursales.dto.RegionRequestDTO;
import com.prueba.ms_sucursales.model.Region;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {

    public RegionDTO toDTO(Region region) {
        if (region == null) return null;

        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setNombre(region.getNombre());
        dto.setCodigo(region.getCodigo());

        return dto;
    }

    public Region toEntity(RegionRequestDTO dto) {
        if (dto == null) return null;

        Region region = new Region();
        region.setNombre(dto.getNombre());
        region.setCodigo(dto.getCodigo());

        return region;
    }
}