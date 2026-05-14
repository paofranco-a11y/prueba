package com.prueba.ms_pedidos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DetallePedidoResponseDTO {
    private Integer id;
    private Integer productoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Boolean descuentoAplicado;
    private LocalDateTime fechaAgregado;
    private String notasAdicionales;
}