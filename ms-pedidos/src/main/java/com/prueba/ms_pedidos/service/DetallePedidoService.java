package com.prueba.ms_pedidos.service;

import com.prueba.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.prueba.ms_pedidos.dto.DetallePedidoResponseDTO;
import com.prueba.ms_pedidos.exception.ResourceNotFoundException;
import com.prueba.ms_pedidos.mapper.DetallePedidoMapper;
import com.prueba.ms_pedidos.model.DetallePedido;
import com.prueba.ms_pedidos.model.Pedido;
import com.prueba.ms_pedidos.repository.DetallePedidoRepository;
import com.prueba.ms_pedidos.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoMapper mapper;

    public List<DetallePedidoResponseDTO> obtenerTodos() {
        log.info("Consultando todos los detalles de pedidos registrados");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public DetallePedidoResponseDTO obtenerPorId(Integer id) {
        log.info("Buscando detalle de pedido con ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Detalle de pedido no encontrado con ID {}", id);
                    return new ResourceNotFoundException("Detalle de pedido no encontrado con ID " + id);
                });
    }

    public DetallePedidoResponseDTO crear(DetallePedidoRequestDTO dto) {
        log.info("Iniciando adicion independiente de item para el pedido ID: {}", dto.getPedidoId());
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID " + dto.getPedidoId()));
        DetallePedido nuevo = mapper.toEntity(dto);
        nuevo.setPedido(pedido);
        DetallePedido guardado = repository.save(nuevo);
        log.info("Detalle de pedido guardado con exito. ID asignado: {}", guardado.getId());
        return mapper.toDTO(guardado);
    }

    public DetallePedidoResponseDTO actualizar(Integer id, DetallePedidoRequestDTO dto) {
        log.info("Iniciando actualizacion manual del detalle ID: {}", id);
        DetallePedido existente = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Detalle no encontrado para actualizar con ID: {}", id);
                    return new ResourceNotFoundException("Detalle de pedido no encontrado con ID " + id);
                });
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido asociado no encontrado con ID " + dto.getPedidoId()));
        existente.setPedido(pedido);
        existente.setProductoId(dto.getProductoId());
        existente.setCantidad(dto.getCantidad());
        existente.setPrecioUnitario(dto.getPrecioUnitario());
        existente.setDescuentoAplicado(dto.getDescuentoAplicado());
        existente.setFechaAgregado(dto.getFechaAgregado());
        DetallePedido actualizado = repository.save(existente);
        log.info("Detalle id {} actualizado correctamente en la DB", actualizado.getId());
        return mapper.toDTO(actualizado);
    }

    public void eliminar(Integer id) {
        log.info("Eliminando detalle de pedido ID: {}", id);
        DetallePedido detalle = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede eliminar el Detalle inexistente con ID " + id));
        repository.delete(detalle);
        log.info("Detalle eliminado correctamente de la base de datos");
    }


    // ---- MÉTODOS HATEOAS (V2) ----

    public List<DetallePedido> listarTodosModel() {
        log.info("HATEOAS - Listando todos los detalles de pedidos");
        return repository.findAll();
    }

    public DetallePedido obtenerModelPorId(Integer id) {
        log.info("HATEOAS - Buscando detalle con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de pedido no encontrado con ID " + id));
    }

    public DetallePedido crearModel(DetallePedido detalle) {
        log.info("HATEOAS - Creando detalle de pedido");
        return repository.save(detalle);
    }

    public DetallePedido actualizarModel(Integer id, DetallePedido detalle) {
        log.info("HATEOAS - Actualizando detalle con ID: {}", id);
        DetallePedido existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de pedido no encontrado con ID " + id));

        existente.setProductoId(detalle.getProductoId());
        existente.setCantidad(detalle.getCantidad());
        existente.setPrecioUnitario(detalle.getPrecioUnitario());
        existente.setDescuentoAplicado(detalle.getDescuentoAplicado());
        existente.setFechaAgregado(detalle.getFechaAgregado());
        existente.setPedido(detalle.getPedido());

        return repository.save(existente);
    }
}