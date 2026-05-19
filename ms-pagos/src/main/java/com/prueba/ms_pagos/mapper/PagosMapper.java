package com.prueba.ms_pagos.mapper;

import com.prueba.ms_pagos.dto.PagosRequestDTO;
import com.prueba.ms_pagos.dto.PagosResponseDTO;
import com.prueba.ms_pagos.model.Pagos;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PagosMapper {

    public Pagos toEntity(PagosRequestDTO dto) {
        if (dto == null) return null;

        Pagos pago = new Pagos();
        pago.setPedidoId(dto.getPedidoId());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setEstado(dto.getEstado());
        pago.setMonto(dto.getMonto());
        return pago;
    }

    public PagosResponseDTO toDTO(Pagos entity) {
        if (entity == null) return null;

        PagosResponseDTO dto = new PagosResponseDTO();
        dto.setId(entity.getId());
        dto.setPedidoId(entity.getPedidoId());
        dto.setMetodoPago(entity.getMetodoPago());
        dto.setFechaPago(LocalDate.from(entity.getFechaPago()));
        dto.setEstado(entity.getEstado());
        dto.setMonto(entity.getMonto());
        return dto;
    }

}
