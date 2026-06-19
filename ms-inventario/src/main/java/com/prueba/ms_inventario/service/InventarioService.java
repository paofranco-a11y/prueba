package com.prueba.ms_inventario.service;


import  com.prueba.ms_inventario.cliente.ProductoCliente;
import com.prueba.ms_inventario.dto.InventarioRequestDTO;
import com.prueba.ms_inventario.dto.InventarioResponseDTO;
import com.prueba.ms_inventario.dto.ProductoResponseDTO;
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

    // Metodo para Obtener todos los objetos
    public List<InventarioResponseDTO> listarTodos() {
        log.info("Consultando todos los registros de inventario");
        return inventarioRepository.findAll().stream()
                .map(inventarioMapper::toDTO)
                .collect(Collectors.toList());
    }


    // Metodo Obtener por un ID especifico
    public InventarioResponseDTO obtenerPorId(Integer id) {
        log.info("Buscando inventario con ID: {}", id);
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Inventario no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Inventario no encontrado con ID " + id);
                });
        // Lo mapeamos al DTO
        InventarioResponseDTO responseDTO = inventarioMapper.toDTO(inventario);
        try {
            ProductoResponseDTO productoDatos = productoCliente.obtenerProducto(inventario.getProductoId());
            responseDTO.setProducto(productoDatos);
            log.info("La informacion del producto fue cargada exitosamente al inventario.");
        } catch (Exception e) {
            log.warn("No se pudo cargar la informacion completa del producto ID {}.", inventario.getProductoId());
        }

        return responseDTO;
    }

    // Metodo para crear un nuevo inventario (incluye validacion con ms-productos)
    public InventarioResponseDTO crear(InventarioRequestDTO dto) {
        log.info("Iniciando creacion de inventario para producto ID: {}", dto.getProductoId());
        try {
            productoCliente.obtenerProducto(dto.getProductoId());
            log.info("Producto validado correctamente");
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            throw new ResourceNotFoundException("No se pudo crear el inventario, el producto con el ID " + dto.getProductoId() + " no existe en el sistema");
        }
        Inventario nuevo = inventarioMapper.toEntity(dto);
        Inventario guardado = inventarioRepository.save(nuevo);
        log.info("Inventario guardado exitosamente con ID: {}", guardado.getId());

        return inventarioMapper.toDTO(guardado);
    }


    // Metodo para actualizar un inventario
    public InventarioResponseDTO actualizar(Integer id, InventarioRequestDTO dto) {
        log.info("Iniciando actualizacion del inventario con ID: {}", id);

        Inventario inventarioExistente = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));

        // Se valida que un producto existe para validar la actualizacion del inventario
        try {
            productoCliente.obtenerProducto(dto.getProductoId());
            log.info("producto validado correctamente");
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            throw new ResourceNotFoundException("No se pudo actualizar el inventario, el producto con el ID " + dto.getProductoId() + " no existe en el sistema");
        }

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

    //Metodo para eliminar un inventario
    public void eliminar(Integer id) {
        log.info("Eliminando inventario con ID: {}", id);
        Inventario producto = inventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede eliminar el Inventario con ID " + id));
        inventarioRepository.delete(producto);
        log.info("Inventario eliminado correctamente");
    }
}












