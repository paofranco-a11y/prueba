package com.prueba.ms_usuario.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Le dice a Spring Boot que esta es una clase de configuración del sistema
@Configuration
public class SwaggerConfig {

    // Se encarga de crear y configurar la interfaz visual de Swagger para Usuarios
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API 2026 USUARIOS E-COMMERCE")
                        .version("1.0")
                        .description("DOCUMENTACIÓN DE LA API PARA LA GESTIÓN DE USUARIOS Y PERFILES"));
    }
}