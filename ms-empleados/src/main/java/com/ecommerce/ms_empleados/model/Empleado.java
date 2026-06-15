package com.ecommerce.ms_empleados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombreCompleto;
    private String correoElectronico;
    private Float sueldoBase;
    private Integer sucursalId;
    private boolean estaActivo;
    private LocalDate fechaIngreso;
}