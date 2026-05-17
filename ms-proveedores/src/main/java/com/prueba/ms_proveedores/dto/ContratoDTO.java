package com.prueba.ms_proveedores.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ContratoDTO {
    private Integer id;
    private String codigoContrato;
    private Double montoTotal;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private boolean vigente;
    private Integer proveedorId;
}