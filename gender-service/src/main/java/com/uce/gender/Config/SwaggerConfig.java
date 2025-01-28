package com.uce.gender.Config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Api Rest Gender",
        version = "1.0",
        description = "This is a api rest gender for created"
    )
)
public class SwaggerConfig {
    
}
