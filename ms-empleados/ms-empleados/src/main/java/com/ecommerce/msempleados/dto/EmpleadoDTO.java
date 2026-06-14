package com.ecommerce.msempleados.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {

    private Integer id;
    private String nombreCompleto;
    private String cargo;
    private String emailCorporativo;
    private Double sueldoBase;
    private Integer aniosExperiencia;
    private boolean activo;
    private LocalDate fechaIngreso;
    private Integer sucursalId;

}
