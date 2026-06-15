package com.ecommerce.ms_envios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "envios")
@Data
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