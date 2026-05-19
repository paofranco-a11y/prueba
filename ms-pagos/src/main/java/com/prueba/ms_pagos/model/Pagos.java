package com.prueba.ms_pagos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Pagos {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "pedido_id", nullable = false)
        private Integer pedidoId;

        @Column(name = "metodo_pago", nullable = false)
        private String metodoPago;

        @Column(name = "fecha_pago")
        private LocalDateTime fechaPago = LocalDateTime.now();

        @Column(nullable = false)
        private String estado;

        @Column(nullable = false)
        private Double monto;



}
