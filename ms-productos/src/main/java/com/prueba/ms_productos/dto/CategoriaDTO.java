package com.prueba.ms_productos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Integer id;
    private String nombre;
    private String codigo;
    private String descripcion;
    private boolean activo;
    private LocalDate fechaVigencia;
}