package com.prueba.ms_inventario.dto;

import lombok.Data;

import java.time.LocalDate;
@Data

public class ProductoResponseDTO {


    private Integer id;
    private String nombre;
    private String codigo;
    private Double precio;
    private Integer stock;
    private boolean activo;
    private LocalDate fechaRegistro;
    private Integer categoriaId;

}
