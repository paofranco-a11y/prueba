package com.prueba.ms_pedidos.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Le dice a Spring Boot que cargue esta configuración al arrancar
@Configuration
public class SwaggerConfig {

    // Configura los textos que saldrán en la página web de Swagger
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API 2026 PEDIDOS E-COMMERCE")
                        .version("1.0")
                        .description("DOCUMENTACIÓN DE LA API PARA EL CONTROL Y PROCESAMIENTO DE PEDIDOS"));
    }
}