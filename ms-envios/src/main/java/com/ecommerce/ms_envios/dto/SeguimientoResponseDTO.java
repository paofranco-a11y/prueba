package com.ecommerce.ms_envios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeguimientoResponseDTO {

    private Integer id;
    private Integer envioId;
    private String estadoActual;
    private String ubicacionActual;
    private Integer porcentajeProgreso;
    private boolean requiereFirma;
    private LocalDate fechaActualizacion;
}