package com.prueba.ms_pagos.cliente;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-pedidos", url = "http://localhost:8084/api/v1/pedidos")
public interface PedidosCliente {

    @PutMapping("/{id}/estado")
    void actualizarEstadoPedido(@PathVariable("id") Integer id, @RequestParam("estado") String estado);
}



