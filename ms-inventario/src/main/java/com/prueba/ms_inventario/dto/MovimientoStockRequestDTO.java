package com.prueba.ms_inventario.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MovimientoStockRequestDTO {

    @NotNull(message = "El ID de inventario es obligatorio")
    private Integer inventarioId;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    private String tipoMovimiento;

    private String motivoRazon;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidadMoviendo;

    @NotNull(message = "El estado de aprobación es obligatorio")
    private Boolean aprobado;

    @NotNull(message = "La fecha de movimiento es obligatoria")
    private LocalDateTime fechaMovimiento;
}
