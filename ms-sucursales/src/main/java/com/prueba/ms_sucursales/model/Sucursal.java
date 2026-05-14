package com.prueba.ms_sucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "sucursal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(nullable = false)
    private LocalDate fechaApertura;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}