package com.prueba.ms_inventario.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MovimientoStockResponseDTO {

    private Integer id;
    private Integer inventarioId;
    private String tipoMovimiento;
    private String motivoRazon;
    private Integer cantidadMoviendo;
    private Boolean aprobado;
    private LocalDateTime fechaMovimiento;
}
