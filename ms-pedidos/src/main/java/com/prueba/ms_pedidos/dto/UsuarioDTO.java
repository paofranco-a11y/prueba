package com.prueba.ms_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

// Recibe la estructura de los datos del cliente mapeados desde el microservicio ms-usuarios a traves de FeignClient

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String email;
    private String telefono;
    private Integer edad;
    private boolean activo;
    private LocalDate fechaRegistro;
}