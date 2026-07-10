package com.prueba.ms_proveedores.service;

import com.prueba.ms_proveedores.dto.ContratoDTO;
import com.prueba.ms_proveedores.dto.ContratoRequestDTO;
import com.prueba.ms_proveedores.exception.ResourceNotFoundException;
import com.prueba.ms_proveedores.mapper.ContratoMapper;
import com.prueba.ms_proveedores.model.Contrato;
import com.prueba.ms_proveedores.model.Proveedor;
import com.prueba.ms_proveedores.repository.ContratoRepository;
import com.prueba.ms_proveedores.repository.ProveedorRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j // Agrega logs automáticos requeridos por la pauta
@Service
@RequiredArgsConstructor
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final ProveedorRepository proveedorRepository;
    private final ContratoMapper contratoMapper;


    public List<ContratoDTO> listarTodos() {
        log.info("Service: Listando todos los contratos");
        List<Contrato> contratos = contratoRepository.findAll();
        return contratos.stream()
                .map(contratoMapper::toDTO)
                .collect(Collectors.toList());
    }


    public ContratoDTO buscarPorId(Integer id) {
        log.info("Service: Buscando contrato con ID: {}", id);
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Service: Contrato con ID {} no encontrado", id);
                    return new ResourceNotFoundException("Contrato no encontrado con el ID: " + id);
                });
        return contratoMapper.toDTO(contrato);
    }


    public ContratoDTO crear(ContratoRequestDTO contratoRequestDTO) {
        log.info("Service: Creando contrato codigo: {} para el proveedor ID: {}",
                contratoRequestDTO.getCodigoContrato(), contratoRequestDTO.getProveedorId());


        Proveedor proveedor = proveedorRepository.findById(contratoRequestDTO.getProveedorId())
                .orElseThrow(() -> new ResourceNotFoundException("No se puede crear el contrato. Proveedor no encontrado con ID: " + contratoRequestDTO.getProveedorId()));

        try {
            Contrato contrato = contratoMapper.toEntity(contratoRequestDTO, proveedor);
            Contrato guardado = contratoRepository.save(contrato);
            return contratoMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Service: Error al guardar el contrato en la base de datos");
            throw e;
        }
    }


    public ContratoDTO actualizar(Integer id, ContratoRequestDTO contratoRequestDTO) {
        log.info("Service: Intentando actualizar contrato con ID: {}", id);

        Contrato contratoExistente = contratoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrato no encontrado con ID: " + id));


        Proveedor proveedor = proveedorRepository.findById(contratoRequestDTO.getProveedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + contratoRequestDTO.getProveedorId()));

        try {

            contratoExistente.setCodigoContrato(contratoRequestDTO.getCodigoContrato());
            contratoExistente.setMontoTotal(contratoRequestDTO.getMontoTotal());
            contratoExistente.setFechaInicio(contratoRequestDTO.getFechaInicio());
            contratoExistente.setFechaTermino(contratoRequestDTO.getFechaTermino());
            contratoExistente.setVigente(contratoRequestDTO.isVigente());
            contratoExistente.setProveedor(proveedor); // Modifica la relación padre

            Contrato actualizado = contratoRepository.save(contratoExistente);
            return contratoMapper.toDTO(actualizado);
        } catch (Exception e) {
            log.error("Service: Error al actualizar el contrato con ID: {}", id);
            throw e;
        }
    }


    public void eliminar(Integer id) {
        log.info("Service: Intentando eliminar contrato con ID: {}", id);
        if (!contratoRepository.existsById(id)) {
            log.error("Service: No se pudo eliminar. Contrato ID {} no existe", id);
            throw new ResourceNotFoundException("No se puede eliminar, contrato no encontrado con ID: " + id);
        }
        try {
            contratoRepository.deleteById(id);
            log.info("Service: Contrato con ID {} eliminado correctamente", id);
        } catch (Exception e) {
            log.error("Service: Error al borrar el contrato con ID: {}", id);
            throw e;
        }
    }
}