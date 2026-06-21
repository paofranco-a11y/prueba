package com.prueba.ms_usuario.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Data //crea automáticamente los métodos Getters y Setters para todas las variables
@NoArgsConstructor  //crea el constructor vacío
@AllArgsConstructor //crea un constructor con todos los parámetros
public class UsuarioRequestDTO {

    // El nombre no puede estar vacío y debe medir entre 2 y 100 caracteres
    @NotBlank
    @Size(min = 2, max = 100)
    private String nombre;

    // El correo es obligatorio y debe tener una estructura de email válida (@...)
    @NotBlank
    @Email
    private String email;

    // El teléfono es obligatorio y debe tener entre 8 y 15 dígitos
    @NotBlank
    @Size(min = 8, max = 15)
    private String telefono;

    // La edad debe ser un número positivo mayor o igual a 1
    @Positive
    @Min(1)
    private Integer edad;

    // Estado de habilitación por defecto (activo)
    private boolean activo = true;

    // La fecha de registro es obligatoria y debe ser actual o pasada
    @NotNull
    @PastOrPresent
    private LocalDate fechaRegistro;
}