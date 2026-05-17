package com.prueba.ms_pagos.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PagosResponseDTO {
    private Integer id;
    private Integer pedidoId;
    private String metodoPago;
    private LocalDate fechaPago;
    private String estado;
    private Double monto;


}
