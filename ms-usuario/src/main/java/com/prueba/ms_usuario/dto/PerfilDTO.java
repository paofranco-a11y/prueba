package com.prueba.ms_usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilDTO {

    private Integer id;
    private String tipoPerfil;
    private String direccion;
    private String descripcion;
    private boolean verificado;
    private LocalDate fechaCreacion;
    private Integer usuarioId;
}
