package com.prueba.ms_proveedores.service;

import com.prueba.ms_proveedores.dto.ProveedorDTO;
import com.prueba.ms_proveedores.dto.ProveedorRequestDTO;
import com.prueba.ms_proveedores.exception.ResourceNotFoundException;
import com.prueba.ms_proveedores.mapper.ProveedorMapper;
import com.prueba.ms_proveedores.model.Proveedor;
import com.prueba.ms_proveedores.repository.ProveedorRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;


    // 1. GET - Listar todos los proveedores
    public List<ProveedorDTO> listarTodos() {
        log.info("Service: Listando todos los proveedores");
        List<Proveedor> proveedores = proveedorRepository.findAll();
        return proveedores.stream()
                .map(proveedorMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 2. GET - Buscar un proveedor por ID
    public ProveedorDTO buscarPorId(Integer id) {
        log.info("Service: Buscando proveedor con ID: {}", id);
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Service: Proveedor con ID {} no fue encontrado", id);
                    return new ResourceNotFoundException("El proveedor no encontrado con el ID: " + id);
                });
        return proveedorMapper.toDTO(proveedor);
    }

    // 3. POST - Crear un nuevo proveedor
    public ProveedorDTO crear(ProveedorRequestDTO proveedorRequestDTO) {
        log.info("Service: Creando nuevo proveedor con nombre: {}", proveedorRequestDTO.getNombre());
        try {
            Proveedor proveedor = proveedorMapper.toEntity(proveedorRequestDTO);
            Proveedor guardado = proveedorRepository.save(proveedor);
            return proveedorMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Service: Error al guardar el proveedor en la base de datos");
            throw e;
        }
    }

    // 4. PUT - Actualizar un proveedor existente
    public ProveedorDTO actualizar(Integer id, ProveedorRequestDTO proveedorRequestDTO) {
        log.info("Service: Intentando actualizar proveedor con ID: {}", id);
        Proveedor proveedorExistente = proveedorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Service: No se pudo actualizar. ID {} no existe", id);
                    return new ResourceNotFoundException("Proveedor no encontrado para actualizar con el ID: " + id);
                });

        try {
            proveedorExistente.setNombre(proveedorRequestDTO.getNombre());
            proveedorExistente.setRut(proveedorRequestDTO.getRut());
            proveedorExistente.setCalificacion(proveedorRequestDTO.getCalificacion());
            proveedorExistente.setActivo(proveedorRequestDTO.isActivo());
            proveedorExistente.setContactoEmail(proveedorRequestDTO.getContactoEmail());

            Proveedor actualizado = proveedorRepository.save(proveedorExistente);
            return proveedorMapper.toDTO(actualizado);
        } catch (Exception e) {
            log.error("Service: Error al actualizar los datos del proveedor con ID: {}", id);
            throw e;
        }
    }

    // 5. DELETE - Eliminar un proveedor por ID
    public void eliminar(Integer id) {
        log.info("Service: Intentando eliminar proveedor con ID: {}", id);
        if (!proveedorRepository.existsById(id)) {
            log.error("Service: No se pudo eliminar. ID {} no existe", id);
            throw new ResourceNotFoundException("No se puede eliminar, proveedor no encontrado con el ID: " + id);
        }
        try {
            proveedorRepository.deleteById(id);
            log.info("Service: Proveedor con ID {} eliminado correctamente", id);
        } catch (Exception e) {
            log.error("Service: Error al ejecutar el borrado del proveedor con ID: {}", id);
            throw e;
        }
    }


    // - Buscar Proveedores Activos Ordenados

    public List<ProveedorDTO> buscarProveedoresActivos() {
        log.info("Service: Buscando lista de proveedores activos usando query nativa");
        // Llama exactamente a tu método con Query nativa
        List<Proveedor> activos = proveedorRepository.findProveedoresActivosOrdenados();
        return activos.stream()
                .map(proveedorMapper::toDTO)
                .collect(Collectors.toList());
    }
}