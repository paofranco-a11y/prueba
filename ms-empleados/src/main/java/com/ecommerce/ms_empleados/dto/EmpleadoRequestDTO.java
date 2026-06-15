package com.ecommerce.ms_empleados.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequestDTO {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe proporcionar un correo electrónico válido")
    private String correoElectronico;

    @NotNull(message = "El sueldo base no puede ser nulo")
    @Positive(message = "El sueldo base debe ser un monto positivo")
    private Float sueldoBase;

    @NotNull(message = "El ID de la sucursal es obligatorio")
    @Min(value = 1, message = "El ID de sucursal debe ser un número válido")
    private Integer sucursalId;

    private boolean estaActivo;

    @NotNull(message = "La fecha de ingreso no puede ser nula")
    @PastOrPresent(message = "La fecha de ingreso debe pertenecer al pasado o presente")
    private LocalDate fechaIngreso;
}
