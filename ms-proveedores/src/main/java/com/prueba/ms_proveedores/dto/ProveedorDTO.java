package com.prueba.ms_proveedores.dto;

import lombok.Data;

@Data
public class ProveedorDTO {
    private Integer id;
    private String nombre;
    private String rut;
    private Integer calificacion;
    private boolean activo;
    private String contactoEmail;
}