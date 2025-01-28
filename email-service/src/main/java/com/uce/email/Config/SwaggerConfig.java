package com.uce.email.Config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Api Rest Email",
        version = "1.0",
        description = "This is a api rest email used for confirmation account"
    )
)
public class SwaggerConfig {
    
}
