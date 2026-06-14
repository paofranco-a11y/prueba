package com.ecommerce.msempleados.service;

import com.ecommerce.msempleados.client.SucursalClient;
import com.ecommerce.msempleados.dto.EmpleadoDTO;
import com.ecommerce.msempleados.dto.EmpleadoRequestDTO;
import com.ecommerce.msempleados.exception.ResourceNotFoundException;
import com.ecommerce.msempleados.mapper.EmpleadoMapper;
import com.ecommerce.msempleados.model.Empleado;
import com.ecommerce.msempleados.repository.EmpleadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoMapper empleadoMapper;

    @Autowired
    private SucursalClient sucursalClient;

    public List<EmpleadoDTO> listarEmpleados() {
        log.info("Consultando todos los registros de empleados");
        try {
            return empleadoRepository.findAll().stream()
                    .map(empleadoMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al listar empleados: {}", e.getMessage());
            throw e;
        }
    }

    public EmpleadoDTO obtenerEmpleadoPorId(Integer id) {
        log.info("Buscando empleado con ID: {}", id);
        try {
            Empleado empleado = empleadoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + id));
            return empleadoMapper.toDTO(empleado);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al buscar empleado por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public EmpleadoDTO crearEmpleado(EmpleadoRequestDTO dto) {
        log.info("Creando un nuevo empleado: {}", dto.getNombreCompleto());
        try {
            log.info("Verificando existencia de sucursal ID: {}", dto.getSucursalId());
            // Validación via FeignClient
            sucursalClient.getSucursalById(dto.getSucursalId());

            Empleado empleado = empleadoMapper.toEntity(dto);
            return empleadoMapper.toDTO(empleadoRepository.save(empleado));
        } catch (Exception e) {
            log.error("Error al crear empleado: {}", e.getMessage());
            throw e;
        }
    }

    public EmpleadoDTO actualizarEmpleado(Integer id, EmpleadoRequestDTO dto) {
        log.info("Actualizando empleado con ID: {}", id);
        try {
            Empleado empleado = empleadoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + id));

            // Seteo de los atributos específicos de tu entidad Empleado
            empleado.setNombreCompleto(dto.getNombreCompleto());
            empleado.setCargo(dto.getCargo());
            empleado.setEmailCorporativo(dto.getEmailCorporativo());
            empleado.setSueldoBase(dto.getSueldoBase());
            empleado.setAniosExperiencia(dto.getAniosExperiencia());
            empleado.setActivo(dto.isActivo());
            empleado.setFechaIngreso(dto.getFechaIngreso());
            empleado.setSucursalId(dto.getSucursalId());

            return empleadoMapper.toDTO(empleadoRepository.save(empleado));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar empleado por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void eliminarEmpleado(Integer id) {
        log.info("Eliminando empleado con ID: {}", id);
        try {
            Empleado empleado = empleadoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontro el empleado para eliminar con ID: " + id));
            empleadoRepository.delete(empleado);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar empleado por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    //Método personalizado manteniendo la estructura de logs y try-catch del estándar
    public List<EmpleadoDTO> buscarPorSucursalYAnio(Integer sucursalId, Integer anio) {
        log.info("Buscando empleados de sucursal {} que ingresaron en el año {}", sucursalId, anio);
        try {
            return empleadoRepository.findBySucursalIdAndAnioIngreso(sucursalId, anio).stream()
                    .map(empleadoMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al buscar empleados por sucursal {} y año {}: {}", sucursalId, anio, e.getMessage());
            throw e;
        }
    }
}