package com.prueba.ms_usuario.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String nombre;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 15)
    private String telefono;

    @Positive
    @Min(1)
    private Integer edad;

    private boolean activo = true;

    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;
}