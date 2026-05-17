package com.prueba.ms_sucursales.service;

import com.prueba.ms_sucursales.dto.RegionDTO;
import com.prueba.ms_sucursales.dto.RegionRequestDTO;
import com.prueba.ms_sucursales.exception.ResourceNotFoundException;
import com.prueba.ms_sucursales.mapper.RegionMapper;
import com.prueba.ms_sucursales.model.Region;
import com.prueba.ms_sucursales.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private RegionMapper regionMapper;

    public List<RegionDTO> listarRegiones() {
        log.info("Consultando todos los registros de regiones");
        try {
            return regionRepository.findAll().stream()
                    .map(regionMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al listar regiones: {}", e.getMessage());
            throw e;
        }
    }

    public RegionDTO obtenerRegionPorId(Integer id) {
        log.info("Buscando region con ID: {}", id);
        try {
            return regionRepository.findById(id)
                    .map(regionMapper::toDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con ID: " + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al buscar region por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public RegionDTO crearRegion(RegionRequestDTO dto) {
        log.info("Creando region: {}", dto.getNombre());
        try {
            Region region = regionMapper.toEntity(dto);
            return regionMapper.toDTO(regionRepository.save(region));
        } catch (Exception e) {
            log.error("Error al crear region: {}", e.getMessage());
            throw e;
        }
    }

    public RegionDTO actualizarRegion(Integer id, RegionRequestDTO dto) {
        log.info("Actualizando region con ID: {}", id);
        try {
            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con ID: " + id));

            region.setNombre(dto.getNombre());
            region.setCodigo(dto.getCodigo());

            return regionMapper.toDTO(regionRepository.save(region));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar region por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void eliminarRegion(Integer id) {
        log.info("Eliminando region con ID: {}", id);
        try {
            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontro la region para eliminar con ID: " + id));
            regionRepository.delete(region);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar region por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}