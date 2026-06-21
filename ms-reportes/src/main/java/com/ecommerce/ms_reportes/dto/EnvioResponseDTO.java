package com.ecommerce.ms_reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvioResponseDTO {
    private Integer id;
    private Integer pedidoId;
    private String direccionDestino;
    private Float costoEnvio;
    private boolean esInternacional;
    private LocalDate fechaDespacho;
}