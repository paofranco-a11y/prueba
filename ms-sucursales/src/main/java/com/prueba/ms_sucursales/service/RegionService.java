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
        try {
            return regionRepository.findAll().stream()
                    .map(RegionMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error al listar regiones: {}", e.getMessage());
            throw e;
        }
    }

    public RegionDTO obtenerRegionPorId(Integer id) {
        log.info("Service: Buscando region ID: {}", id);
        try {
            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con ID: " + id));
            return RegionMapper.toDTO(region);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al buscar region por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public RegionDTO crearRegion(RegionRequestDTO dto) {
        log.info("Service: Creando region: {}", dto.getNombre());
        try {
            Region region = RegionMapper.toEntity(dto);
            return RegionMapper.toDTO(regionRepository.save(region));
        } catch (Exception e) {
            log.error("Error al crear region: {}", e.getMessage());
            throw e;
        }
    }

    public RegionDTO actualizarRegion(Integer id, RegionRequestDTO dto) {
        log.info("Service: Actualizando region ID: {}", id);
        try {
            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con ID: " + id));

            region.setNombre(dto.getNombre());
            region.setCodigo(dto.getCodigo());

            return RegionMapper.toDTO(regionRepository.save(region));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar region por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void eliminarRegion(Integer id) {
        log.info("Service: Eliminando region ID: {}", id);
        try {
            if (!regionRepository.existsById(id)) {
                throw new ResourceNotFoundException("No se encontro la region para eliminar con ID: " + id);
            }
            regionRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar region por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}