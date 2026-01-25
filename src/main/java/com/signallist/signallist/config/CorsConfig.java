package com.signallist.signallist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String frontendUrl = System.getenv("FRONTEND_URL");

                List<String> allowedOriginsList = new ArrayList<>();

                allowedOriginsList.add("http://localhost:3000");

                if (frontendUrl != null && !frontendUrl.isEmpty()) {
                    allowedOriginsList.add(frontendUrl);
                }

                String[] allowedOrigins = allowedOriginsList.toArray(new String[0]);

                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedOriginPatterns("https://*.vercel.app")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}

