package com.ecommerce.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-envios", url = "http://localhost:8086/api/v1/envios")
public interface EnvioClient {
    @GetMapping
    List<Object> obtenerEnviosParaReporte(); // Estado de envíos para el reporte
}