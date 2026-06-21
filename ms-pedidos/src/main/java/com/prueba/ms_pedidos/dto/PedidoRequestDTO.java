package com.prueba.ms_pedidos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDTO {

    // Validacion para asegurar que la solicitud incluya obligatoriamente el identificador del cliente comprador
    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer clienteId;

    // Valida que el codigo no este en blanco y limita su longitud de caracteres permitida
    @NotBlank(message = "El codigo de seguimiento no puede estar vacío")
    @Size(min = 5, max = 50, message = "El codigo debe tener entre 5 y 50 caracteres")
    private String codigoSeguimiento;

    // Asegura que el monto total de la orden se envie obligatoriamente y que sea un numero positivo
    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a cero")
    private Double total;

    // Validacion para obligar al cliente a escribir la ubicacion exacta del despacho
    @NotBlank(message = "La dirección de envoo es obligatoria")
    private String direccionEnvio;

    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    @Valid
    private List<DetallePedidoRequestDTO> detalles;
}