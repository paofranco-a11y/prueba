package com.prueba.ms_pedidos.dto;

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

    // Contiene los datos del cliente consultados de forma remota a su microservicio
    private UsuarioDTO usuario;

    // Lista con el desglose detallado de todos los productos y cantidades que componen este pedido
    private List<DetallePedidoResponseDTO> detalles;
}