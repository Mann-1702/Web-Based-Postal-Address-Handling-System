package edu.seattleu.addressmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Address Manager API")
                        .description("API for addresses Search by state, city, and partial address")
                        .version("1.0.0")
                        .contact(new Contact().name("Team 4")
                                .email("amehta3@seattleu.edu,bmehta1@seattleu.edu,mshah4@seattleu.edu,mosman@seattleu.edu")));
    }
}
