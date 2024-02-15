package com.psalles.multiworkJ17.commons.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// https://howtodoinjava.com/spring5/webmvc/spring-mvc-cors-configuration/#global-cors

@Configuration
public class CorsConfig {

    @Value("#{'${crossOrigin}'.split(',')}")
    private String[] crossOrigin;


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(crossOrigin);
            }
        };
    }
}
