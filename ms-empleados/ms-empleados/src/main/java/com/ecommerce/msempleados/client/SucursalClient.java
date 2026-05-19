package com.ecommerce.msempleados.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;


@FeignClient(name = "ms-sucursales", url = "http://localhost:8088")
public interface SucursalClient {

    @GetMapping("/api/v1/sucursales/{id}")
    Map<String, Object> getSucursalById(@PathVariable("id") Integer id);

}
