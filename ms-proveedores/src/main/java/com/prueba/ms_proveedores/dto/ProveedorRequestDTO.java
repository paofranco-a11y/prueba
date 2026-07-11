package com.prueba.ms_proveedores.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProveedorRequestDTO {

    @NotBlank(message = "El nombre del proveedor no puede estar vacio o contener solo espacios")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El RUT del proveedor es un campo obligatorio")
    private String rut;

    @NotNull(message = "La calificación no puede ser nula")
    @Min(value = 1, message = "La calificacion minima permitida es 1")
    @Max(value = 5, message = "La calificacion maxima permitida es 5")
    private Integer calificacion;

    private boolean activo;

    @NotBlank(message = "El correo de contacto no puede estar vacio")
    @jakarta.validation.constraints.Email(message = "Debe proporcionar una dirección de correo electronico valida")
    private String contactoEmail;
}