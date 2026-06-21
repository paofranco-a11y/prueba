package com.prueba.ms_usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data //crea automáticamente los métodos Getters y Setters para todas las variables
@NoArgsConstructor  //crea el constructor vacío
@AllArgsConstructor //crea un constructor con todos los parámetros


public class PerfilRequestDTO {

    // El tipo de perfil no puede estar vacío y debe tener entre 2 y 50 caracteres
    @NotBlank
    @Size(min = 2, max = 50)
    private String tipoPerfil;

    // La dirección es obligatoria y debe tener entre 5 y 150 caracteres
    @NotBlank
    @Size(min = 5, max = 150)
    private String direccion;

    // La descripción es obligatoria y acepta un máximo de 255 caracteres
    @NotBlank
    @Size(min = 2, max = 255)
    private String descripcion;

    // Estado inicial de verificación (por defecto falso)
    private boolean verificado = false;

    // La fecha es obligatoria y debe ser de hoy o de un día pasado
    @NotNull
    @PastOrPresent
    private LocalDate fechaCreacion;

    // ID obligatorio del usuario al que se le asignará este perfil
    @NotNull
    private Integer usuarioId;
}