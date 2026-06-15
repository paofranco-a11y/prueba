package com.ecommerce.ms_envios.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoExternoDTO {
    private Integer id;
    private Integer clienteId;
    private String codigoSeguimiento;
    private LocalDateTime fechaPedido;
    private Double total;
    private Boolean pagado;
    private String direccionEnvio;
    private List<Object> detalles; // O una clase específica si tienes el DetalleDTO
}