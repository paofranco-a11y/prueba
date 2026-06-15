package com.ecommerce.ms_empleados.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoResponseDTO {
    private Integer id;
    private String nombreCompleto;
    private String correoElectronico;
    private Float sueldoBase;
    private Integer sucursalId;
    private boolean estaActivo;
    private LocalDate fechaIngreso;
}
