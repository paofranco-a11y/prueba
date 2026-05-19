package com.prueba.ms_inventario.service;

import com.prueba.ms_inventario.dto.MovimientoStockRequestDTO;
import com.prueba.ms_inventario.dto.MovimientoStockResponseDTO;
import com.prueba.ms_inventario.exception.ResourceNotFoundException;
import com.prueba.ms_inventario.mapper.MovimientoStockMapper;
import com.prueba.ms_inventario.model.Inventario;
import com.prueba.ms_inventario.model.MovimientoStock;
import com.prueba.ms_inventario.repository.InventarioRepository;
import com.prueba.ms_inventario.repository.MovimientoStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovimientoStockService {

    @Autowired
    private MovimientoStockRepository repository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private MovimientoStockMapper mapper;


    public List<MovimientoStockResponseDTO> obtenerTodos() {
        log.info("Consultando todos los movimientos de stock");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    public MovimientoStockResponseDTO obtenerPorId(Integer id) {
        log.info("Buscando movimiento de stock con ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Movimiento no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Movimiento no encontrado con ID " + id);
                });
    }


    public MovimientoStockResponseDTO crear(MovimientoStockRequestDTO dto) {
        log.info("Iniciando registro de movimiento para inventario ID: {}", dto.getInventarioId());

        Inventario inventario = inventarioRepository.findById(dto.getInventarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado con ID " + dto.getInventarioId()));

        MovimientoStock nuevo = mapper.toEntity(dto);
        nuevo.setInventario(inventario);

        MovimientoStock guardado = repository.save(nuevo);
        log.info("Movimiento guardado con éxito. ID: {}", guardado.getId());
        return mapper.toDTO(guardado);
    }


    public MovimientoStockResponseDTO actualizar(Integer id, MovimientoStockRequestDTO dto) {
        log.info("Iniciando actualización manual del movimiento ID: {}", id);

        MovimientoStock existente = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error: Movimiento no encontrado para actualizar con ID: {}", id);
                    return new ResourceNotFoundException("Movimiento no encontrado con ID " + id);
                });

        Inventario inventario = inventarioRepository.findById(dto.getInventarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado con ID " + dto.getInventarioId()));

        existente.setInventario(inventario);
        existente.setTipoMovimiento(dto.getTipoMovimiento());
        existente.setMotivoRazon(dto.getMotivoRazon());
        existente.setCantidadMoviendo(dto.getCantidadMoviendo());
        existente.setAprobado(dto.getAprobado());
        existente.setFechaMovimiento(dto.getFechaMovimiento());

        MovimientoStock actualizado = repository.save(existente);
        log.info("Movimiento stock ID {} actualizado correctamente en DB", actualizado.getId());
        return mapper.toDTO(actualizado);
    }


    public void eliminar(Integer id) {
        log.info("Eliminando movimiento de stock ID: {}", id);
        MovimientoStock movimiento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede eliminar Movimiento inexistente con ID: " + id));
        repository.delete(movimiento);
        log.info("Movimiento eliminado correctamente");
    }
}