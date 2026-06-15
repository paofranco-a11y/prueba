package com.ecommerce.ms_envios.client;

import com.ecommerce.ms_envios.dto.PedidoExternoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pedidos", url = "http://localhost:8084/api/v1/pedidos")
public interface PedidoClient {

    @GetMapping("/{id}")
    PedidoExternoDTO obtenerPedidoPorId(@PathVariable("id") Integer id);
}