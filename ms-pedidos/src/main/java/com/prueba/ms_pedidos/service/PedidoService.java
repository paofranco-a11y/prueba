package com.prueba.ms_pedidos.service;
import com.prueba.ms_pedidos.cliente.ProductoCliente;
import com.prueba.ms_pedidos.cliente.UsuarioCliente;
import com.prueba.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.prueba.ms_pedidos.dto.PedidoRequestDTO;
import com.prueba.ms_pedidos.dto.PedidoResponseDTO;
import com.prueba.ms_pedidos.exception.ResourceNotFoundException;
import com.prueba.ms_pedidos.mapper.PedidoMapper;
import com.prueba.ms_pedidos.model.Pedido;
import com.prueba.ms_pedidos.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private PedidoMapper mapper;

    @Autowired
    private UsuarioCliente usuarioCliente;

    @Autowired
    private ProductoCliente productoCliente;

    public List<PedidoResponseDTO> obtenerTodos() {
        log.info("Iniciando consulta de todos los pedidos");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO obtenerPorId(Integer id) {
        log.info("Buscando pedido con ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID " + id));
    }

    public PedidoResponseDTO crear(PedidoRequestDTO dto) {
        log.info("Iniciando creación de pedido para cliente ID: {}", dto.getClienteId());

        // 1. Validar Usuario (ms-usuarios)
        try {
            usuarioCliente.validarUsuario(dto.getClienteId());
            log.info("Usuario validado correctamente");
        } catch (Exception e) {
            log.error("Error al validar usuario: {}", e.getMessage());
            throw new RuntimeException("No se puede crear el pedido Cliente no encontrado.");
        }

        // 2. Validar cada Producto (ms-productos)
        for (DetallePedidoRequestDTO detalle : dto.getDetalles()) {
            try {
                productoCliente.obtenerProducto(detalle.getProductoId());
                log.info("Producto ID {} validado", detalle.getProductoId());
            } catch (Exception e) {
                log.error("Error al validar producto {}: {}", detalle.getProductoId(), e.getMessage());
                throw new RuntimeException("Producto con ID " + detalle.getProductoId() + " no disponible.");
            }
        }

        // 3. Mapear, Guardar y Retornar (Aquí es donde se completa la acción)
        Pedido pedido = mapper.toEntity(dto);
        Pedido guardado = repository.save(pedido);

        log.info("Pedido guardado con éxito. ID: {}", guardado.getId());
        return mapper.toDTO(guardado);
    }

    public List<PedidoResponseDTO> obtenerPagados() {
        log.info("Consultando pedidos pagados");
        return repository.findPedidosPagados().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public void eliminar(Integer id) {
        log.info("Eliminando pedido ID: {}", id);
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede eliminar Pedido inexistente"));
        repository.delete(pedido);
    }
}

