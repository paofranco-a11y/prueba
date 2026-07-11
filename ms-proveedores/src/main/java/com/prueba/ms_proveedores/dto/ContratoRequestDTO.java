package com.prueba.ms_proveedores.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ContratoRequestDTO {

    @NotBlank(message = "El codigo de contrato es obligatorio para el registro")
    private String codigoContrato;

    @NotNull(message = "El monto total del contrato no puede ser nulo")
    @Positive(message = "El monto total debe ser un valor estrictamente positivo")
    @DecimalMin(value ="1.0", message ="El monto total mínimo debe ser de 1.0")
    private Double montoTotal;

    @NotNull(message = "Debe especificar de manera obligatoria la fecha de inicio del contrato")
    private LocalDate fechaInicio;

    @NotNull(message = "Debe especificar de manera obligatoria la fecha de termino del contrato")
    private LocalDate fechaTermino;

    private boolean vigente;

    @NotNull(message = "Debe asociar el contrato a un ID de proveedor valido")
    private Integer proveedorId;
}