package com.example.masterplanbbe.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("LocalTime", new StringSchema().example("14:30:30")))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("PLAN-B REST API Specifications")
                .description("Specification")
                .version("1.0.0");
    }

}




