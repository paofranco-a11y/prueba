package com.ecommerce.ms_envios.mapper;

import com.ecommerce.ms_envios.dto.EnvioRequestDTO;
import com.ecommerce.ms_envios.dto.EnvioResponseDTO;
import com.ecommerce.ms_envios.model.Envio;

public class EnvioMapper {
    public static Envio toEntity(EnvioRequestDTO dto) {
        if (dto == null) return null;
        Envio envio = new Envio();
        envio.setPedidoId(dto.getPedidoId());
        envio.setDireccionDestino(dto.getDireccionDestino());
        envio.setCostoEnvio(dto.getCostoEnvio());
        envio.setEsInternacional(dto.isEsInternacional());
        envio.setFechaDespacho(dto.getFechaDespacho());
        return envio;
    }

    public static EnvioResponseDTO toDTO(Envio entity) {
        if (entity == null) return null;
        EnvioResponseDTO dto = new EnvioResponseDTO();
        dto.setId(entity.getId());
        dto.setPedidoId(entity.getPedidoId());
        dto.setDireccionDestino(entity.getDireccionDestino());
        dto.setCostoEnvio(entity.getCostoEnvio());
        dto.setEsInternacional(entity.isEsInternacional());
        dto.setFechaDespacho(entity.getFechaDespacho());
        return dto;
    }
}
