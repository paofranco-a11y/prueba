package com.prueba.ms_inventario.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.awt.SystemColor.info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("MS_INVENTARIO")
                .version("1.0")
                .description("DOCUMENTACION DE LA API PARA EL SISTEMA DE INVENTARIO"));
    }

}
