package com.prueba.ms_sucursales.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {

    private Integer id;
    private String nombre;
    private String direccion;
    private String telefono;
    private Integer capacidad;
    private boolean activo;
    private LocalDate fechaApertura;
    private Integer regionId;
}
