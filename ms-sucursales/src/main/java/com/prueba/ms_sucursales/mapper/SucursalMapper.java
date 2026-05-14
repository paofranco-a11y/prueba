package com.prueba.ms_sucursales.mapper;

import com.prueba.ms_sucursales.dto.SucursalDTO;
import com.prueba.ms_sucursales.dto.SucursalRequestDTO;
import com.prueba.ms_sucursales.model.Region;
import com.prueba.ms_sucursales.model.Sucursal;

public class SucursalMapper {

    public static SucursalDTO toDTO(Sucursal sucursal) {
        return new SucursalDTO(
                sucursal.getId(),
                sucursal.getNombre(),
                sucursal.getDireccion(),
                sucursal.getTelefono(),
                sucursal.getCapacidad(),
                sucursal.isActivo(),
                sucursal.getFechaApertura(),
                sucursal.getRegion().getId()
        );
    }

    public static Sucursal toEntity(SucursalRequestDTO dto, Region region) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setTelefono(dto.getTelefono());
        sucursal.setCapacidad(dto.getCapacidad());
        sucursal.setActivo(dto.isActivo());
        sucursal.setFechaApertura(dto.getFechaApertura());
        sucursal.setRegion(region);
        return sucursal;
    }
}