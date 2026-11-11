package br.edu.ifes.locadora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI locadoraOpenApi() {
        return new OpenAPI().info(new Info()
                .title("API Sistema Videolocadora")
                .version("Sprint 2")
                .description("Servicos REST do Controle de Acervo: atores, diretores, classes, titulos e itens. "
                        + "Erros seguem o formato ErroPadrao (400 dados invalidos, 404 nao encontrado, 409 conflito)."));
    }
}
