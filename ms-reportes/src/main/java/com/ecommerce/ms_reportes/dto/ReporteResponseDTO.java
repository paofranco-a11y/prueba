package com.ecommerce.ms_reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteResponseDTO {

    private Integer id;
    private String titulo;
    private String tipoReporte;
    private Float totalIngresos;
    private Integer totalRegistros;
    private boolean esConsolidado;
    private LocalDate fechaGeneracion;

    private PedidoResponseDTO pedido;
    private PagosResponseDTO pago;
    private EnvioResponseDTO envio;
}
