package com.prueba.ms_pedidos.cliente;

import com.prueba.ms_pedidos.dto.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-usuarios", url = "http://localhost:8081/api/v1/usuarios")
public interface UsuarioCliente {
    @GetMapping("/{id}")
    UsuarioResponseDTO validarUsuario(@PathVariable("id") Integer id);
}
