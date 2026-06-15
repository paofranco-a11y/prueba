package com.ecommerce.ms_reportes.dto;

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
public class ReporteRequestDTO {

    @NotBlank(message = "El título del reporte es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe contener entre 3 y 100 caracteres")
    private String titulo;

    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String tipoReporte;

    @PositiveOrZero(message = "Los ingresos deben ser una cantidad positiva o cero")
    private Float totalIngresos;

    @Min(value = 0, message = "El total de registros no puede ser menor a cero")
    private Integer totalRegistros;

    private boolean esConsolidado;

    @NotNull(message = "La fecha de generación no puede ser nula")
    @PastOrPresent(message = "La fecha debe pertenecer al pasado o al presente")
    private LocalDate fechaGeneracion;
}
