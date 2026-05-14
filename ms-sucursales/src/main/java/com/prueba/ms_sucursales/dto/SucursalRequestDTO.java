package com.prueba.ms_sucursales.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequestDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String nombre;

    @NotBlank
    @Size(min = 5, max = 150)
    private String direccion;

    @NotBlank
    @Size(min = 8, max = 15)
    private String telefono;

    @Positive
    @Min(1)
    private Integer capacidad;

    private boolean activo = true;

    @NotNull
    @PastOrPresent
    private LocalDate fechaApertura;

    @NotNull
    private Integer regionId;
}