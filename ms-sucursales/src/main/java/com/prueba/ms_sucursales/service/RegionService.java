package com.prueba.ms_sucursales.service;

import com.prueba.ms_sucursales.dto.RegionDTO;
import com.prueba.ms_sucursales.dto.RegionRequestDTO;
import com.prueba.ms_sucursales.exception.ResourceNotFoundException;
import com.prueba.ms_sucursales.mapper.RegionMapper;
import com.prueba.ms_sucursales.model.Region;
import com.prueba.ms_sucursales.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public List<RegionDTO> listarRegiones() {
        log.info("Service: Listando todas las regiones");
        return regionRepository.findAll().stream()
                .map(RegionMapper::toDTO)
                .toList();
    }

    public RegionDTO obtenerRegionPorId(Integer id) {
        log.info("Service: Buscando region ID: {}", id);
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con ID: " + id));
        return RegionMapper.toDTO(region);
    }

    public RegionDTO crearRegion(RegionRequestDTO dto) {
        log.info("Service: Guardando nueva region: {}", dto.getNombre());
        Region region = RegionMapper.toEntity(dto);
        return RegionMapper.toDTO(regionRepository.save(region));
    }

    public RegionDTO actualizarRegion(Integer id, RegionRequestDTO dto) {
        log.info("Service: Actualizando region ID: {}", id);
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar, region no encontrada"));

        region.setNombre(dto.getNombre());
        region.setCodigo(dto.getCodigo());
        region.setDescripcion(dto.getDescripcion());
        region.setNumeroComunas(dto.getNumeroComunas());
        region.setActivo(dto.isActivo());

        return RegionMapper.toDTO(regionRepository.save(region));
    }

    public void eliminarRegion(Integer id) {
        log.info("Service: Eliminando region ID: {}", id);
        if (!regionRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontro la region para eliminar");
        }
        regionRepository.deleteById(id);
    }
}