package com.prueba.ms_pedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetallePedidoRequestDTO {

    // Validacion para asegurar que el ID del pedido padre no llegue vacio desde el cliente
    @NotNull(message = "El ID de pedido es obligatorio")
    private Integer pedidoId;

    // Validacion para obligar a que se envie el identificador del producto a comprar
    @NotNull(message = "El ID de producto es obligatorio")
    private Integer productoId;

    // Valida que el campo no este vacio y restringe que la cantidad minima agregada sea de al menos 1 unidad
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    // Validacion para asegurar que el costo del producto venga registrado de forma obligatoria
    @NotNull(message = "El precio unitario es obligatorio")
    private Double precioUnitario;

    // Campo opcional para indicar si el item cuenta con alguna rebaja o descuento
    private Boolean descuentoAplicado;

    // Campo opcional para transferir el registro del momento exacto en que se genera esta solicitud
    private LocalDateTime fechaAgregado;

}
