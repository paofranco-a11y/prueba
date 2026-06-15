package com.ecommerce.ms_empleados.service;

import com.ecommerce.ms_empleados.client.SucursalClient;
import com.ecommerce.ms_empleados.dto.EmpleadoRequestDTO;
import com.ecommerce.ms_empleados.dto.EmpleadoResponseDTO;
import com.ecommerce.ms_empleados.mapper.EmpleadoMapper;
import com.ecommerce.ms_empleados.model.Empleado;
import com.ecommerce.ms_empleados.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoService.class);

    private final EmpleadoRepository empleadoRepository;
    private final SucursalClient sucursalClient;

    public EmpleadoService(EmpleadoRepository empleadoRepository, SucursalClient sucursalClient) {
        this.empleadoRepository = empleadoRepository;
        this.sucursalClient = sucursalClient;
    }

    public List<EmpleadoResponseDTO> findAll() {
        log.info("Ejecutando método findAll para listar todos los empleados"); // Log obligatorio
        return empleadoRepository.findAll().stream()
                .map(EmpleadoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EmpleadoResponseDTO findById(Integer id) {
        log.info("Ejecutando método findById para el empleado con ID: {}", id); // Log obligatorio
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ResourceNotFound: Empleado no encontrado con ID: " + id));
        return EmpleadoMapper.toDTO(empleado);
    }

    public EmpleadoResponseDTO save(EmpleadoRequestDTO dto) {
        log.info("Ejecutando método save para registrar a: {}", dto.getNombreCompleto()); // Log obligatorio
        try {
            // Comunicación Feign: Valida si la sucursal realmente existe antes de guardar
            sucursalClient.obtenerSucursalPorId(dto.getSucursalId());
            log.info("Sucursal ID: {} validada exitosamente vía FeignClient", dto.getSucursalId());

            Empleado empleado = EmpleadoMapper.toEntity(dto);
            Empleado guardado = empleadoRepository.save(empleado);
            return EmpleadoMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Error crítico en la persistencia o validación de sucursal con Feign: {}", e.getMessage()); // Log error
            throw new RuntimeException("No se pudo guardar el empleado. La sucursal no existe o el servicio no responde.");
        }
    }

    public EmpleadoResponseDTO update(Integer id, EmpleadoRequestDTO dto) {
        log.info("Ejecutando método update para el empleado con ID: {}", id);
        Empleado existente = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ResourceNotFound: El empleado solicitado no existe")); // Gatilla 404

        // Actualización de campos de manera individual conforme a la nota del examen
        existente.setNombreCompleto(dto.getNombreCompleto());
        existente.setCorreoElectronico(dto.getCorreoElectronico());
        existente.setSueldoBase(dto.getSueldoBase());
        existente.setSucursalId(dto.getSucursalId());
        existente.setEstaActivo(dto.isEstaActivo());
        existente.setFechaIngreso(dto.getFechaIngreso());

        return EmpleadoMapper.toDTO(empleadoRepository.save(existente));
    }

    public void delete(Integer id) {
        log.info("Ejecutando método delete para eliminar al empleado con ID: {}", id);
        Empleado existente = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ResourceNotFound: El empleado no existe"));
        empleadoRepository.delete(existente);
    }
}
