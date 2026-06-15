package com.ecommerce.ms_envios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "seguimientos")
@Data                       // Genera Getters, Setters, toString, equals y hashCode automáticamente
@NoArgsConstructor          // Constructor vacío obligatorio para Hibernate/JPA
@AllArgsConstructor         // Constructor con todos los campos para crearlo fácilmente en tu código
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "envio_id", nullable = false)
    private Envio envio;

    private String estadoActual;
    private String ubicacionActual;
    private Integer porcentajeProgreso;
    private boolean requiereFirma;
    private LocalDate fechaActualizacion;
}