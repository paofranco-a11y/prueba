package com.prueba.ms_inventario.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// El nombre "ms-productos" debe coincidir con el nombre del servicio destino
@FeignClient(name = "ms-productos", url = "http://localhost:8081")

public interface ProductoCliente {
    @GetMapping("/api/productos/{id}")
    Object validarProducto(@PathVariable("id") Integer id);
}