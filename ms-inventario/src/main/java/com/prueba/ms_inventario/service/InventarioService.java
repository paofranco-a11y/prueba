package com.prueba.ms_inventario.service;


import  com.prueba.ms_inventario.cliente.ProductoCliente;
import com.prueba.ms_inventario.dto.InventarioRequestDTO;
import com.prueba.ms_inventario.dto.InventarioResponseDTO;
import com.prueba.ms_inventario.exception.ResourceNotFoundException;
import com.prueba.ms_inventario.mapper.InventarioMapper;
import com.prueba.ms_inventario.model.Inventario;
import com.prueba.ms_inventario.repository.InventarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private InventarioMapper inventarioMapper;

    @Autowired
    private ProductoCliente productoCliente;

    public List<InventarioResponseDTO> listarTodos() {
        log.info("Consultando todos los registros de inventario");
        return inventarioRepository.findAll().stream()
                .map(inventarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public InventarioResponseDTO obtenerPorId(Integer id) {
        log.info("Buscando inventario con ID: {}", id);
        return inventarioRepository.findById(id)
                .map(inventarioMapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Inventario no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Inventario no encontrado con ID " + id);
                });
    }

    public InventarioResponseDTO crear(InventarioRequestDTO dto) {
        log.info("Iniciando creación de inventario para producto ID: {}", dto.getProductoId());
            productoCliente.validarProducto(dto.getProductoId());
            log.info("Producto validado correctamente");


        Inventario nuevo = inventarioMapper.toEntity(dto);
        Inventario guardado = inventarioRepository.save(nuevo);
        log.info("Inventario guardado exitosamente con ID: {}", guardado.getId());

        return inventarioMapper.toDTO(guardado);
    }

    public InventarioResponseDTO actualizar(Integer id, InventarioRequestDTO dto) {
        log.info("Iniciando actualizacion del inventario con ID: {}", id);

        Inventario inventarioExistente = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));

        inventarioExistente.setProductoId(dto.getProductoId());
        inventarioExistente.setUbicacionBodega(dto.getUbicacionBodega());
        inventarioExistente.setCantidadDisponible(dto.getCantidadDisponible());
        inventarioExistente.setStockMinimoAlerta(dto.getStockMinimoAlerta());
        inventarioExistente.setActivo(dto.getActivo());
        inventarioExistente.setFechaUltimaRevision(dto.getFechaUltimaRevision());

        Inventario inventarioActualizado = inventarioRepository.save(inventarioExistente);
        log.info("Inventario con ID: {} actualizado correctamente en DB", id);
        return inventarioMapper.toDTO(inventarioActualizado);

    }


    public void eliminar(Integer id) {
        log.info("Eliminando producto del inventario ID: {}", id);
        Inventario producto = inventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede eliminar el Producto con ID " + id));
        inventarioRepository.delete(producto);
        log.info("Producto eliminado correctamente");
    }
}












