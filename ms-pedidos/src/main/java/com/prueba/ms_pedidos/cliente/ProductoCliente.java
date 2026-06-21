package com.prueba.ms_pedidos.cliente;

import com.prueba.ms_pedidos.dto.ProductoDTO; // <-- Importamos tu DTO
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


// Define el cliente Feign para conectarse al microservicio externo ms-productos
@FeignClient(name = "ms-productos", url = "http://localhost:8082/api/v1/productos")
public interface ProductoCliente {

    @GetMapping("/{id}")
    ProductoDTO obtenerProducto(@PathVariable("id") Integer id); // <-- Tipado con ProductoDTO
}