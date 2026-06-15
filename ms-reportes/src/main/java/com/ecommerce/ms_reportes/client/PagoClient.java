package com.ecommerce.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-pagos", url = "http://localhost:8085/api/v1/pagos")
public interface PagoClient {
    @GetMapping
    List<Object> obtenerPagosParaReporte(); // Consolida pagos para el reporte
}