package com.prueba.ms_sucursales.service;

import com.prueba.ms_sucursales.dto.SucursalDTO;
import com.prueba.ms_sucursales.dto.SucursalRequestDTO;
import com.prueba.ms_sucursales.exception.ResourceNotFoundException;
import com.prueba.ms_sucursales.mapper.SucursalMapper;
import com.prueba.ms_sucursales.model.Region;
import com.prueba.ms_sucursales.model.Sucursal;
import com.prueba.ms_sucursales.repository.RegionRepository;
import com.prueba.ms_sucursales.repository.SucursalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SucursalMapper sucursalMapper;

    public List<SucursalDTO> listarSucursales() {
        log.info("Consultando todos los registros de sucursales");
        try {
            return sucursalRepository.findAll().stream()
                    .map(sucursalMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al listar sucursales: {}", e.getMessage());
            throw e;
        }
    }

    public SucursalDTO obtenerSucursalPorId(Integer id) {
        log.info("Buscando sucursal con ID: {}", id);
        try {
            return sucursalRepository.findById(id)
                    .map(sucursalMapper::toDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con ID: " + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al buscar sucursal por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public SucursalDTO crearSucursal(SucursalRequestDTO dto) {
        log.info("Creando sucursal: {}", dto.getNombre());
        try {
            Region region = regionRepository.findById(dto.getRegionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con ID: " + dto.getRegionId()));

            Sucursal sucursal = sucursalMapper.toEntity(dto, region);
            return sucursalMapper.toDTO(sucursalRepository.save(sucursal));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al crear sucursal: {}", e.getMessage());
            throw e;
        }
    }

    public SucursalDTO actualizarSucursal(Integer id, SucursalRequestDTO dto) {
        log.info("Actualizando sucursal con ID: {}", id);
        try {
            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con ID: " + id));

            Region region = regionRepository.findById(dto.getRegionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Nueva region no encontrada con ID: " + dto.getRegionId()));

            sucursal.setNombre(dto.getNombre());
            sucursal.setDireccion(dto.getDireccion());
            sucursal.setTelefono(dto.getTelefono());
            sucursal.setCapacidad(dto.getCapacidad());
            sucursal.setActivo(dto.isActivo());
            sucursal.setFechaApertura(dto.getFechaApertura());
            sucursal.setRegion(region);

            return sucursalMapper.toDTO(sucursalRepository.save(sucursal));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar sucursal por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void eliminarSucursal(Integer id) {
        log.info("Eliminando sucursal con ID: {}", id);
        try {
            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontro la sucursal para eliminar con ID: " + id));
            sucursalRepository.delete(sucursal);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar sucursal por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // --- EL MÉTODO pers ---
    public List<SucursalDTO> buscarPorNombreRegion(String nombreRegion) {
        log.info("Buscando sucursales para la region: {}", nombreRegion);
        try {
            return sucursalRepository.findSucursalesByNombreRegion(nombreRegion).stream()
                    .map(sucursalMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al buscar sucursales por region {}: {}", nombreRegion, e.getMessage());
            throw e;
        }
    }
}