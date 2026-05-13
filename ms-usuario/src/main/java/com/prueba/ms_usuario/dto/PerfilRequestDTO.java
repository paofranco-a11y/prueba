package com.prueba.ms_usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilRequestDTO {

    @NotBlank
    @Size(min = 2, max = 50)
    private String tipoPerfil;

    @NotBlank
    @Size(min = 5, max = 150)
    private String direccion;

    @NotBlank
    @Size(min = 2, max = 255)
    private String descripcion;

    private boolean verificado = false;

    @NotNull
    @PastOrPresent
    private LocalDate fechaCreacion;

    @NotNull
    private Integer usuarioId;
}