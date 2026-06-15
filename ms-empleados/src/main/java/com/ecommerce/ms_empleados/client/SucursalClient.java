package com.ecommerce.ms_empleados.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-sucursales", url = "http://localhost:8088/api/v1/sucursales")
public interface SucursalClient {
    @GetMapping("/{id}")
    Object obtenerSucursalPorId(@PathVariable("id") Integer id);
}