package com.prueba.ms_pedidos.mapper;

import com.prueba.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.prueba.ms_pedidos.dto.DetallePedidoResponseDTO;
import com.prueba.ms_pedidos.model.DetallePedido;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DetallePedidoMapper {


    public DetallePedidoResponseDTO toDTO(DetallePedido entity) {
        if (entity == null) return null;
        DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO();
        dto.setId(entity.getId());
        dto.setProductoId(entity.getProductoId());
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setDescuentoAplicado(entity.getDescuentoAplicado());
        dto.setFechaAgregado(entity.getFechaAgregado());

        if (entity.getPedido() != null) {
            dto.setPedidoId(entity.getPedido().getId());
        }
        return dto;
    }

    public DetallePedido toEntity(DetallePedidoRequestDTO dto) {
        if (dto == null) return null;
        DetallePedido entity = new DetallePedido();
        entity.setProductoId(dto.getProductoId());
        entity.setCantidad(dto.getCantidad());
        entity.setPrecioUnitario(dto.getPrecioUnitario());
        entity.setDescuentoAplicado(dto.getDescuentoAplicado() != null ? dto.getDescuentoAplicado() : false);
        entity.setFechaAgregado(dto.getFechaAgregado() != null ? dto.getFechaAgregado() : LocalDateTime.now());
        return entity;
    }
}