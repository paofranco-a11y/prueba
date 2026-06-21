package com.prueba.ms_pagos.cliente;


import com.prueba.ms_pagos.dto.PedidoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-pedidos", url = "http://localhost:8084/api/v1/pedidos")
public interface PedidosCliente {

    @GetMapping("/{id}")
    PedidoResponseDTO obtenerPedido(@PathVariable("id") Integer id);

    @PutMapping("/{id}/estado")
    PedidoResponseDTO actualizarEstadoPedido(@PathVariable("id") Integer id, @RequestParam("estado") String estado);
}



