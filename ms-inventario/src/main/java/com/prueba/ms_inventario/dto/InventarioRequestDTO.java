package com.prueba.ms_inventario.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InventarioRequestDTO {
    @NotNull(message = "El ID del producto es obligatorio")
    @Min(value = 1, message = "El ID del producto debe ser mayor a 0")
    private Integer productoId;

    @NotBlank(message = "La ubicación de la bodega no puede estar vacía")
    private String ubicacionBodega;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer cantidadDisponible;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimoAlerta;

    @NotNull(message = "Debe indicar si el inventario está activo (true/false)")
    private Boolean activo;

    @NotNull(message = "La fecha de última revisión es obligatoria")
    private LocalDate fechaUltimaRevision;
}
