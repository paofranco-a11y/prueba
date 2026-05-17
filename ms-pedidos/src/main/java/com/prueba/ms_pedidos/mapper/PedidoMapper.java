package com.prueba.ms_pedidos.mapper;


import com.prueba.ms_pedidos.dto.*;
import com.prueba.ms_pedidos.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {
    public PedidoResponseDTO toDTO(Pedido entity) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(entity.getId());
        dto.setClienteId(entity.getClienteId());
        dto.setCodigoSeguimiento(entity.getCodigoSeguimiento());
        dto.setFechaPedido(entity.getFechaPedido());
        dto.setTotal(entity.getTotal());
        dto.setPagado(entity.getPagado());
        dto.setDireccionEnvio(entity.getDireccionEnvio());

        dto.setDetalles(entity.getDetalles().stream().map(this::toDetalleDTO).collect(Collectors.toList()));
        return dto;
    }

    private DetallePedidoResponseDTO toDetalleDTO(DetallePedido entity) {
        DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO();
        dto.setId(entity.getId());
        dto.setProductoId(entity.getProductoId());
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setDescuentoAplicado(entity.getDescuentoAplicado());
        dto.setFechaAgregado(entity.getFechaAgregado());
        return dto;
    }

    public Pedido toEntity(PedidoRequestDTO dto) {
        Pedido entity = new Pedido();
        entity.setClienteId(dto.getClienteId());
        entity.setCodigoSeguimiento(dto.getCodigoSeguimiento());
        entity.setTotal(dto.getTotal());
        entity.setDireccionEnvio(dto.getDireccionEnvio());

        List<DetallePedido> detalles = dto.getDetalles().stream().map(d -> {
            DetallePedido detalle = new DetallePedido();
            detalle.setProductoId(d.getProductoId());
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(d.getPrecioUnitario());
            detalle.setDescuentoAplicado(d.getDescuentoAplicado());
            detalle.setPedido(entity);
            return detalle;
        }).collect(Collectors.toList());

        entity.setDetalles(detalles);
        return entity;
    }

}
