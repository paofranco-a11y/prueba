package com.prueba.ms_inventario.service;


import com.prueba.ms_inventario.cliente.ProductoCliente;
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
                    log.error("Error: Inventario no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Inventario no encontrado con ID: " + id);
                });
    }


    public InventarioResponseDTO crear(InventarioRequestDTO dto) {
        log.info("Iniciando creación de inventario para producto ID: {}", dto.getProductoId());
        // Intento de validación con el otro microservicio (Feign)
        try {
            productoCliente.validarProducto(dto.getProductoId());
            log.info("Producto validado correctamente");
        } catch (Exception e) {
            log.warn("No se pudo conectar con ms-productos. Se procede con la creación local.");
        }

        Inventario nuevo = inventarioMapper.toEntity(dto);
        Inventario guardado = inventarioRepository.save(nuevo);
        log.info("Inventario guardado exitosamente con ID: {}", guardado.getId());

        return inventarioMapper.toDTO(guardado);
    }











}
