package com.ecommerce.ms_envios.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data                       // Genera Getters, Setters, toString, equals y hashCode automáticamente
@NoArgsConstructor          // Constructor vacío por defecto
@AllArgsConstructor         // Constructor con todos los campos (muy útil para builder manual)
public class EnvioRequestDTO {

    @NotNull
    private Integer pedidoId;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(min = 5, max = 150)
    private String direccionDestino;

    @Positive(message = "El costo debe ser un valor positivo")
    private Float costoEnvio;

    private boolean esInternacional;

    @NotNull
    @PastOrPresent
    private LocalDate fechaDespacho;
}