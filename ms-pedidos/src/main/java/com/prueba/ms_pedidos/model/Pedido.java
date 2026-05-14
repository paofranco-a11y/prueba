package com.prueba.ms_pedidos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "pedidos")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    @Column(name = "codigo_seguimiento", nullable = false, length = 50)
    private String codigoSeguimiento;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido = LocalDateTime.now();

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private Boolean pagado = false;

    @Column(name = "direccion_envio", nullable = false, length = 100)
    private String direccionEnvio;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;
}