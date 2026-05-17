package com.prueba.ms_proveedores.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProveedorRequestDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String nombre;

    @NotBlank
    private String rut;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer calificacion;

    private boolean activo;

    @NotBlank
    private String contactoEmail;
}