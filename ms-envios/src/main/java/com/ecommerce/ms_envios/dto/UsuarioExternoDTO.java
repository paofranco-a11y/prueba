package com.ecommerce.ms_envios.dto;

import lombok.Data;

@Data
public class UsuarioExternoDTO {
    private Integer id;
    private String nombre; // Ajusta según los atributos definidos en ms-usuarios
    private String email;
}
