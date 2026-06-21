package com.prueba.ms_pedidos.cliente;

import com.prueba.ms_pedidos.dto.UsuarioDTO; // <-- Importamos tu DTO
import com.prueba.ms_pedidos.dto.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Define el cliente Feign para conectarse al microservicio externo ms-productos
@FeignClient(name = "ms-usuarios", url = "http://localhost:8081/api/v1/usuarios")
public interface UsuarioCliente {

    @GetMapping("/{id}")
    UsuarioDTO obtenerUsuario(@PathVariable("id") Integer id); // <-- Tipado con UsuarioDTO
}
    UsuarioResponseDTO validarUsuario(@PathVariable("id") Integer id);
}
