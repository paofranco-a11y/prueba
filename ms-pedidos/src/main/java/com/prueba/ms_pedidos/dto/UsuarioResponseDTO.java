package com.prueba.ms_pedidos.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class UsuarioResponseDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String telefono;
    private Integer edad;
    private boolean activo;
    private LocalDate fechaRegistro;
}
