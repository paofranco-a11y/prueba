package com.ecommerce.ms_envios.client;

import com.ecommerce.ms_envios.dto.UsuarioExternoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", url = "http://localhost:8081/api/v1/usuarios")
public interface UsuarioClient {

    @GetMapping("/{id}")
    UsuarioExternoDTO obtenerUsuarioPorId(@PathVariable("id") Integer id);
}