package com.prueba.ms_inventario.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "producto_id", nullable = false)
    private Integer productoId;

    @Column(name = "ubicacion_bodega", nullable = false, length = 100)
    private String ubicacionBodega;

    @Column(name = "cantidad_disponible", nullable = false)
    private Integer cantidadDisponible;

    @Column(name = "stock_minimo_alerta", nullable = false)
    private Integer stockMinimoAlerta;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "fecha_ultima_revision", nullable = false)
    private LocalDate fechaUltimaRevision;

    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL)
    private List<MovimientoStock> movimientos;
}
