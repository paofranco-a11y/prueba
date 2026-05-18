package com.prueba.ms_pedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetallePedidoRequestDTO {

    @NotNull(message = "El ID de pedido es obligatorio")
    private Integer pedidoId;

    @NotNull(message = "El ID de producto es obligatorio")
    private Integer productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    private Double precioUnitario;

    private Boolean descuentoAplicado;

    private LocalDateTime fechaAgregado;

}
