package com.prueba.ms_sucursales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {

    private Integer id;
    private String nombre;
    private String codigo;
    private String descripcion;
    private Integer numeroComunas;
    private boolean activo;
    private LocalDate fechaCreacion;
}