/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/29/2023
 * Time: 7:46 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes(AUTHORIZATION_HEADER,
                                new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name(AUTHORIZATION_HEADER)
                                        .bearerFormat("JWT")))
//                                new SecurityScheme().type(SecurityScheme.Type.APIKEY)
//                                        .in(SecurityScheme.In.HEADER)
//                                        .name(AUTHORIZATION_HEADER)))
                .info(apiInfo())
                .addSecurityItem(
                        new SecurityRequirement().addList(AUTHORIZATION_HEADER, Collections.singletonList("global")));
    }

    private Info apiInfo() {
        return new Info()
                .title("Spring Boot Order Management System REST APIs")
                .description("Spring Boot Order Management System REST API Documentation")
                .version("1")
                .termsOfService("Terms of service")
                .contact(new Contact()
                        .name("Salah Aldin Dar AlDeek")
                        .url("https://salahaldin2021.github.io/")
                        .email("salahaldin.daraldeek@gmail.com"))
                .license(new License().name("License of API").url("https://www.webservicecomp438ordermanagmentapi.com/license"));
    }
}
