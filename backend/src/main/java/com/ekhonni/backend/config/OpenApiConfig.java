package com.ekhonni.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Author: Asif Iqbal
 * Date: 1/4/25
 */

@Configuration
public class OpenApiConfig {

    @Value("${spring.constant.public.urls}")
    private String[] publicUrls;
    @Value("${spring.constant.user.urls}")
    private String[] userUrls;
    @Value("${spring.constant.admin.urls}")
    private String[] adminUrls;


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ekhonni API Documentation")
                        .description("API documentation for Ekhonni e-commerce platform")
                        .version("2.0")
                        .contact(new Contact()
                                .name("Asif Iqbal")
                                .email("asif783810@gmail.com")
                                .url("https://ekhonni.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development server"),
                        new Server()
                                .url("https://api.ekhonni.com")
                                .description("Production server")))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("1. Public APIs")
//                .pathsToMatch(Arrays.toString(publicUrls))
//                .addOpenApiCustomizer(openApi -> openApi.info(new Info()
//                        .title("Public APIs")
//                        .description("Authentication and public endpoints")))
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi userApi() {
//        return GroupedOpenApi.builder()
//                .group("2. User APIs")
//                .pathsToMatch(Arrays.toString(userUrls))
//                .addOpenApiCustomizer(openApi -> openApi.info(new Info()
//                        .title("User APIs")
//                        .description("User-specific operations")))
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("3. Admin APIs")
//                .pathsToMatch(Arrays.toString(adminUrls))
//                .addOpenApiCustomizer(openApi -> openApi.info(new Info()
//                        .title("Admin APIs")
//                        .description("Administrative operations")))
//                .build();
//    }
}