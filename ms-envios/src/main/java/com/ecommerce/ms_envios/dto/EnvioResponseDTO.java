package com.ecommerce.ms_envios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data                       // Genera Getters, Setters, toString, equals y hashCode automáticamente
@NoArgsConstructor          // Constructor vacío por defecto
@AllArgsConstructor         // Constructor con todos los campos
@Builder                    // Te permite construir este DTO de forma fluida (ej. EnvioResponseDTO.builder().id(1)...)
public class EnvioResponseDTO {

    private Integer id;
    private Integer pedidoId;
    private String direccionDestino;
    private Float costoEnvio;
    private boolean esInternacional;
    private LocalDate fechaDespacho;
}