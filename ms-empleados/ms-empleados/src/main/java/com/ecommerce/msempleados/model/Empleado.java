package com.ecommerce.msempleados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "cargo", nullable = false, length = 80)
    private String cargo;

    @Column(name = "email_corporativo", nullable = false, length = 120)
    private String emailCorporativo;

    @Column(name = "sueldo_base", nullable = false)
    private Double sueldoBase;

    @Column(name = "anios_experiencia", nullable = false)
    private Integer aniosExperiencia;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;


    @Column(name = "sucursal_id", nullable = false)
    private Integer sucursalId;

}
