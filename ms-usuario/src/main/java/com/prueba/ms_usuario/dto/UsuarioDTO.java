package com.prueba.ms_usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data //crea automáticamente los métodos Getters y Setters para todas las variables
@NoArgsConstructor  //crea el constructor vacío
@AllArgsConstructor //crea un constructor con todos los parámetros

public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String telefono;
    private Integer edad;
    private boolean activo;
    private LocalDate fechaRegistro;
}