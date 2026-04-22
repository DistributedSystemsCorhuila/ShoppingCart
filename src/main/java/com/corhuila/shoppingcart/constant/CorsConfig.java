package com.corhuila.shoppingcart.constant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Configuración CORS para permitir comunicación con el frontend Angular (localhost:4200).
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Orígenes permitidos
        config.setAllowedOrigins(List.of(
                "http://localhost:4200",   // Angular dev
                "http://localhost:3000"    // React / otros (opcional)
        ));

        // Métodos HTTP permitidos
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Cabeceras permitidas
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "X-Requested-With"
        ));

        // Permitir credenciales (cookies, Authorization header)
        config.setAllowCredentials(true);

        // Tiempo de caché del preflight (en segundos)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // aplica a todos los endpoints

        return new CorsFilter(source);
    }
}
