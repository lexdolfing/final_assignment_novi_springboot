package com.novi.DemoDrop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
CORS (Cross Origin Resource Sharing is een instelling die zorgt dat de frontend en de backend met elkaar kunnen
communiceren ondanks dat ze op verschillende poorten opereren (b.v. localhost:3000 en localhost:8080).
De globale cors configuratie zorgt dat je niet boven elke klasse @CrossOrigin hoeft te zetten.
In deze applictie staat alsnog wel boven elke klasse ter demonstratie.
Vergeet niet om in de security config ook de ".cors()" optie aan te zetten.
 */


@Configuration
public class GlobalCorsConfiguration
{
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }
}