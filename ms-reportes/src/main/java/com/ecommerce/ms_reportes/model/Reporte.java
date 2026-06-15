package com.ecommerce.ms_reportes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
    private String tipoReporte;
    private Float totalIngresos;
    private Integer totalRegistros;
    private boolean esConsolidado;
    private LocalDate fechaGeneracion;
}