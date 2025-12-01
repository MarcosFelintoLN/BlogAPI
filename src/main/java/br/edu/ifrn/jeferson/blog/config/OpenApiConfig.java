package br.edu.ifrn.jeferson.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Blog API",
                version = "1.0",
                description = "Documentação da API do Blog"
        )
)
public class OpenApiConfig {
}
