package com.prueba.ms_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

// Recibe la estructura de los datos del producto mapeados desde el microservicio ms-productos a traves de FeignCLIENT

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Integer id;
    private String nombre;
    private String codigo;
    private Double precio;
    private Integer stock;
    private boolean activo;
    private LocalDate fechaRegistro;
    private Integer categoriaId;
}