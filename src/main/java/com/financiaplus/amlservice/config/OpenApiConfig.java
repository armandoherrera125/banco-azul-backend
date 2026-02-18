package com.financiaplus.amlservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI amlServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AML & Onboarding API - Banco Azul")
                        .description("API para validación AML, onboarding digital y gestión de clientes.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo AML")));
    }
}