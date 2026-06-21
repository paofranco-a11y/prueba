package com.ecommerce.ms_reportes.client;

import com.ecommerce.ms_reportes.dto.PedidoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-pedidos", url = "http://localhost:8084/api/v1/pedidos")
public interface PedidoClient {
    @GetMapping
    List<PedidoResponseDTO> obtenerPedidosParaReporte(); // <-- Cambiado de Object a PedidoResponseDTO
}