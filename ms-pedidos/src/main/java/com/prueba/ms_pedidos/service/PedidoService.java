package com.prueba.ms_pedidos.service;

import com.prueba.ms_pedidos.cliente.ProductoCliente;
import com.prueba.ms_pedidos.cliente.UsuarioCliente;
import com.prueba.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.prueba.ms_pedidos.dto.DetallePedidoResponseDTO;
import com.prueba.ms_pedidos.dto.PedidoRequestDTO;
import com.prueba.ms_pedidos.dto.PedidoResponseDTO;
import com.prueba.ms_pedidos.dto.ProductoDTO;
import com.prueba.ms_pedidos.dto.UsuarioDTO;
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

    private PedidoResponseDTO enriquecerPedido(PedidoResponseDTO responseDTO) {
        if (responseDTO == null) return null;
        try {
            if (responseDTO.getClienteId() != null) {
                UsuarioDTO usuario = usuarioCliente.obtenerUsuario(responseDTO.getClienteId());
                responseDTO.setUsuario(usuario);
            }
        } catch (Exception e) {
            log.error("No se pudo obtener el usuario para el pedido ID {}: {}", responseDTO.getId(), e.getMessage());
        }
        if (responseDTO.getDetalles() != null) {
            for (DetallePedidoResponseDTO detalle : responseDTO.getDetalles()) {
                try {
                    if (detalle.getProductoId() != null) {
                        ProductoDTO producto = productoCliente.obtenerProducto(detalle.getProductoId());
                        detalle.setProducto(producto);
                    }
                } catch (Exception e) {
                    log.error("No se pudo obtener el producto ID {} para el detalle: {}", detalle.getProductoId(), e.getMessage());
                }
            }
        }
        return responseDTO;
    }

    public List<PedidoResponseDTO> obtenerTodos() {
        log.info("Iniciando consulta de todos los pedidos");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .map(this::enriquecerPedido)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO obtenerPorId(Integer id) {
        log.info("Buscando pedido con ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDTO)
                .map(this::enriquecerPedido)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID " + id));
    }

    public PedidoResponseDTO crear(PedidoRequestDTO dto) {
        log.info("Iniciando creación de pedido para cliente ID: {}", dto.getClienteId());
        try {
            usuarioCliente.obtenerUsuario(dto.getClienteId());
            log.info("Usuario validado correctamente");
        } catch (Exception e) {
            log.error("Error al validar usuario: {}", e.getMessage());
            throw new RuntimeException("No se puede crear el pedido. Cliente no encontrado.");
        }
        for (DetallePedidoRequestDTO detalle : dto.getDetalles()) {
            try {
                productoCliente.obtenerProducto(detalle.getProductoId());
                log.info("Producto ID {} validado", detalle.getProductoId());
            } catch (Exception e) {
                log.error("Error al validar producto {}: {}", detalle.getProductoId(), e.getMessage());
                throw new RuntimeException("Producto con ID " + detalle.getProductoId() + " no disponible.");
            }
        }
        Pedido pedido = mapper.toEntity(dto);
        Pedido guardado = repository.save(pedido);
        log.info("Pedido guardado con éxito. ID: {}", guardado.getId());
        return enriquecerPedido(mapper.toDTO(guardado));
    }

    public PedidoResponseDTO actualizar(Integer id, PedidoRequestDTO dto) {
        log.info("Iniciando actualizacion manual del pedido ID: {}", id);
        Pedido pedidoExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID " + id));
        pedidoExistente.setClienteId(dto.getClienteId());
        pedidoExistente.setCodigoSeguimiento(dto.getCodigoSeguimiento());
        pedidoExistente.setDireccionEnvio(dto.getDireccionEnvio());
        pedidoExistente.setTotal(dto.getTotal());
        Pedido actualizado = repository.save(pedidoExistente);
        log.info("Pedido ID: {} actualizado con exito", actualizado.getId());
        return enriquecerPedido(mapper.toDTO(actualizado));
    }

    public void actualizarEstado(Integer id, String nuevoEstado) {
        log.info("Recibido estado '{}' para el pedido ID: {}", nuevoEstado, id);
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID " + id));
        if ("PAGADO".equalsIgnoreCase(nuevoEstado)) {
            pedido.setPagado(true);
        } else {
            pedido.setPagado(false);
        }
        repository.save(pedido);
        log.info("Pedido ID {} actualizado en DB con pagado = {}", id, pedido.getPagado());
    }

    public List<PedidoResponseDTO> obtenerPagados() {
        log.info("Consultando pedidos pagados");
        return repository.findPedidosPagados().stream()
                .map(mapper::toDTO)
                .map(this::enriquecerPedido)
                .collect(Collectors.toList());
    }

    public void eliminar(Integer id) {
        log.info("Eliminando pedido ID: {}", id);
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede eliminar el pedido inexistente"));
        repository.delete(pedido);
    }


    // ---- MÉTODOS HATEOAS (V2) ----

    public List<Pedido> listarTodosModel() {
        log.info("HATEOAS - Listando todos los pedidos");
        return repository.findAll();
    }

    public Pedido obtenerModelPorId(Integer id) {
        log.info("HATEOAS - Buscando pedido con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID " + id));
    }

    public Pedido crearModel(Pedido pedido) {
        log.info("HATEOAS - Creando pedido");
        return repository.save(pedido);
    }

    public Pedido actualizarModel(Integer id, Pedido pedido) {
        log.info("HATEOAS - Actualizando pedido con ID: {}", id);
        Pedido existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID " + id));

        existente.setClienteId(pedido.getClienteId());
        existente.setCodigoSeguimiento(pedido.getCodigoSeguimiento());
        existente.setDireccionEnvio(pedido.getDireccionEnvio());
        existente.setTotal(pedido.getTotal());
        existente.setPagado(pedido.getPagado());

        return repository.save(existente);
    }
}