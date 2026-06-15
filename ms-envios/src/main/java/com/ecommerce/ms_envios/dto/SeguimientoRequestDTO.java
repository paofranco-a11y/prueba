package com.ecommerce.ms_envios.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeguimientoRequestDTO {

    @NotNull(message = "El ID del envío asociado es obligatorio")
    private Integer envioId;

    @NotBlank(message = "El estado actual no puede estar vacío")
    private String estadoActual; // Ej: "En camino", "Clasificado en centro de distribución"

    @NotBlank(message = "La ubicación actual es obligatoria")
    private String ubicacionActual; // Ej: "Madrid, España"

    @NotNull(message = "El porcentaje de progreso es obligatorio")
    @Min(value = 0, message = "El progreso mínimo es 0%")
    @Max(value = 100, message = "El progreso máximo es 100%")
    private Integer porcentajeProgreso;

    private boolean requiereFirma;

    @NotNull(message = "La fecha de actualización es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser del futuro")
    private LocalDate fechaActualizacion;
}