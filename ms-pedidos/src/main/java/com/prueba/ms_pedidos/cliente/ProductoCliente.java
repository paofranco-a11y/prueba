package com.prueba.ms_pedidos.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-productos", url = "http://localhost:8082/api/v1/productos")
public interface ProductoCliente {

    @GetMapping("/{id}")
    Object obtenerProducto(@PathVariable("id") Integer id);
}