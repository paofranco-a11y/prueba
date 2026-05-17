package com.prueba.ms_pagos.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PagosRequestDTO {

    @NotNull(message = "El ID del pedido es obligatorio")
    @Min(value = 1, message = "El ID del pedido debe ser mayor a 0")
    private Integer pedidoId;

    @NotBlank(message = "El método de pago no puede estar vacio")
    private String metodoPago;

    @NotBlank(message = "El estado del pago no puede estar vacio")
    private String estado;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser un valor mayor a cero")
    private Double monto;

}
