package com.prueba.ms_pagos.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data


public class PedidoResponseDTO {


    private Integer id;
    private Integer clienteId;
    private String codigoSeguimiento;
    private LocalDateTime fechaPedido;
    private Double total;
    private Boolean pagado;
    private String direccionEnvio;

}
