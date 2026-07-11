package com.ecommerce.ms_envios.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "seguimientos")
@NoArgsConstructor          // Constructor vacio obligatorio para Hibernate/JPA
@AllArgsConstructor         // Constructor con todos los campos para crearlo facilmente en tu codigo
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