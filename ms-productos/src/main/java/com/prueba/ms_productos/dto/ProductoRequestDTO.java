package com.prueba.ms_productos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String nombre;

    @NotBlank
    @Size(min = 2, max = 50)
    private String codigo;

    @NotNull
    @Positive
    private Double precio;

    @NotNull
    @Min(0)
    private Integer stock;

    private boolean activo = true;

    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;

    @NotNull
    private Integer categoriaId;
}