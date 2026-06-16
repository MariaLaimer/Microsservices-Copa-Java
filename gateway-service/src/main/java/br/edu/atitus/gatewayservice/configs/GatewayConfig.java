package br.edu.atitus.gatewayservice.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewayConfig {

    @Bean
    RouteLocator getGatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/get").uri("http://httpbin.org"))
                .route(p -> p.path("/products/**").uri("lb://product-service"))
                .route(p -> p.path("/ws/products/**").uri("lb://product-service"))
                .route(p -> p.path("/currency/**").uri("lb://currency-service"))
                .route(p -> p.path("/ws/currency/**").uri("lb://currency-service"))
                .route(p -> p.path("/auth/**").uri("lb://auth-service"))
                .route(p -> p.path("/ws/orders/**").uri("lb://order-service"))
                .build();
    }


    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        
       
        corsConfig.addAllowedOriginPattern("*"); 
        
        
        corsConfig.addAllowedMethod("*"); 
        
        
        corsConfig.addAllowedHeader("*"); 
        
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
