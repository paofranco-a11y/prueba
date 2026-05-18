package com.prueba.ms_productos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String nombre;

    @NotBlank
    @Size(min = 2, max = 10)
    private String codigo;

    @NotBlank
    @Size(min = 2, max = 255)
    private String descripcion;

    private boolean activo = true;

    @NotNull
    @PastOrPresent
    private LocalDate fechaVigencia;
}