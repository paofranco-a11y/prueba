package com.prueba.ms_proveedores.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ContratoRequestDTO {

    @NotBlank
    private String codigoContrato;

    @NotNull
    @Positive
    @DecimalMin("1.0")
    private Double montoTotal;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaTermino;

    private boolean vigente;

    @NotNull
    private Integer proveedorId;
}