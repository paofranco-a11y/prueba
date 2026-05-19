package com.prueba.ms_inventario.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class MovimientoStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "inventario_id", nullable = false)
    private Inventario inventario;

    @Column(name = "Tipo_movimiento", nullable = false, length = 50)
    private String tipoMovimiento;

    @Column(name = "motivo_razon", length = 200)
    private String motivoRazon;

    @Column(name = "cantidad_moviendo", nullable = false)
    private Integer cantidadMoviendo;

    @Column(nullable = false)
    private Boolean aprobado;

    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDateTime fechaMovimiento;
}
