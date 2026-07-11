package com.ecommerce.ms_envios.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "envios")
@NoArgsConstructor
@AllArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer pedidoId;
    private String direccionDestino;
    private Float costoEnvio;
    private boolean esInternacional;
    private LocalDate fechaDespacho;
}