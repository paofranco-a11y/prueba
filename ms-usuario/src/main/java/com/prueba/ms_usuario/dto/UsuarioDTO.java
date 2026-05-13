package com.prueba.ms_usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String telefono;
    private Integer edad;
    private boolean activo;
    private LocalDate fechaRegistro;
}