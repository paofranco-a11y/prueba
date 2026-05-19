package com.ecommerce.msempleados.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoRequestDTO {

    @NotBlank
    @Size(min = 5, max = 100, message = "El nombre debe tener entre 5 y 100 caracteres")
    private String nombreCompleto;

    @NotBlank
    @Size(min = 2, max = 80, message = "El cargo debe tener entre 2 y 80 caracteres")
    private String cargo;

    @NotBlank
    @Email(message = "El email corporativo no tiene formato válido")
    private String emailCorporativo;

    @NotNull
    @DecimalMin(value = "0.0", message = "El sueldo base no puede ser negativo")
    private Double sueldoBase;

    @NotNull
    @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
    private Integer aniosExperiencia;

    @NotNull(message = "Debe indicar si el empleado está activo (true o false)")
    private boolean activo = true;


    @NotNull
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate fechaIngreso;

    @NotNull
    @Positive(message = "El ID de sucursal debe ser positivo")
    private Integer sucursalId;

}
