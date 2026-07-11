package com.ecommerce.ms_envios.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("MS_ENVIOS")
                .version("1.0")
                .description("DOCUMENTACION DE LA API PARA EL SISTEMA DE ENVIOS"));
    }

}
