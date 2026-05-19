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

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer clienteId;

    @NotBlank(message = "El codigo de seguimiento no puede estar vacío")
    @Size(min = 5, max = 50, message = "El codigo debe tener entre 5 y 50 caracteres")
    private String codigoSeguimiento;

    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a cero")
    private Double total;

    @NotBlank(message = "La dirección de envoo es obligatoria")
    private String direccionEnvio;

    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    @Valid
    private List<DetallePedidoRequestDTO> detalles;
}