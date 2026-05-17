package com.prueba.ms_sucursales.service;

import com.prueba.ms_sucursales.dto.SucursalDTO;
import com.prueba.ms_sucursales.dto.SucursalRequestDTO;
import com.prueba.ms_sucursales.exception.ResourceNotFoundException;
import com.prueba.ms_sucursales.mapper.SucursalMapper;
import com.prueba.ms_sucursales.model.Region;
import com.prueba.ms_sucursales.model.Sucursal;
import com.prueba.ms_sucursales.repository.RegionRepository;
import com.prueba.ms_sucursales.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SucursalService {

    private final SucursalRepository sucursalRepository;
    private final RegionRepository regionRepository;

    public List<SucursalDTO> listarSucursales() {
        log.info("Service: Listando todas las sucursales");
        try {
            return sucursalRepository.findAll().stream()
                    .map(SucursalMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error al listar sucursales: {}", e.getMessage());
            throw e;
        }
    }

    public SucursalDTO obtenerSucursalPorId(Integer id) {
        log.info("Service: Buscando sucursal ID: {}", id);
        try {
            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con ID: " + id));
            return SucursalMapper.toDTO(sucursal);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al buscar sucursal por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public SucursalDTO crearSucursal(SucursalRequestDTO dto) {
        log.info("Service: Creando sucursal en region ID: {}", dto.getRegionId());
        try {
            Region region = regionRepository.findById(dto.getRegionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Region no encontrada con ID: " + dto.getRegionId()));

            Sucursal sucursal = SucursalMapper.toEntity(dto, region);
            return SucursalMapper.toDTO(sucursalRepository.save(sucursal));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al crear sucursal: {}", e.getMessage());
            throw e;
        }
    }

    public SucursalDTO actualizarSucursal(Integer id, SucursalRequestDTO dto) {
        log.info("Service: Actualizando sucursal ID: {}", id);
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
            sucursal.setRegion(region);

            return SucursalMapper.toDTO(sucursalRepository.save(sucursal));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar sucursal por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void eliminarSucursal(Integer id) {
        log.info("Service: Eliminando sucursal ID: {}", id);
        try {
            if (!sucursalRepository.existsById(id)) {
                throw new ResourceNotFoundException("No se encontro la sucursal para eliminar con ID: " + id);
            }
            sucursalRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar sucursal por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public List<SucursalDTO> buscarPorNombreRegion(String nombreRegion) {
        log.info("Service: Buscando sucursales por region: {}", nombreRegion);
        try {
            return sucursalRepository.findSucursalesByNombreRegion(nombreRegion).stream()
                    .map(SucursalMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error en metodo personalizado buscarPorNombreRegion: {}", e.getMessage());
            throw e;
        }
    }
}