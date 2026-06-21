package com.prueba.ms_pedidos.mapper;

import com.prueba.ms_pedidos.dto.PedidoRequestDTO;
import com.prueba.ms_pedidos.dto.PedidoResponseDTO;
import com.prueba.ms_pedidos.model.DetallePedido;
import com.prueba.ms_pedidos.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    @Autowired
    private DetallePedidoMapper detalleMapper;

    // Se encarga de armar la estructura del pedido con los datos recibidos del formulario para poder guardarla
    public PedidoResponseDTO toDTO(Pedido entity) {
        if (entity == null) return null;

        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(entity.getId());
        dto.setClienteId(entity.getClienteId());
        dto.setCodigoSeguimiento(entity.getCodigoSeguimiento());
        dto.setFechaPedido(entity.getFechaPedido());
        dto.setTotal(entity.getTotal());
        dto.setPagado(entity.getPagado());
        dto.setDireccionEnvio(entity.getDireccionEnvio());

        if (entity.getDetalles() != null) {
            dto.setDetalles(entity.getDetalles().stream()
                    .map(detalleMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
    // Se encarga de armar la estructura del pedido con los datos recibidos del formulario para poder guardarla
    public Pedido toEntity(PedidoRequestDTO dto) {
        if (dto == null) return null;

        Pedido entity = new Pedido();
        entity.setClienteId(dto.getClienteId());
        entity.setCodigoSeguimiento(dto.getCodigoSeguimiento());
        entity.setTotal(dto.getTotal());
        entity.setDireccionEnvio(dto.getDireccionEnvio());

        if (dto.getDetalles() != null) {
            List<DetallePedido> detalles = dto.getDetalles().stream().map(d -> {
                DetallePedido detalle = detalleMapper.toEntity(d);
                detalle.setPedido(entity);
                return detalle;
            }).collect(Collectors.toList());

            entity.setDetalles(detalles);
        }
        return entity;
    }
}