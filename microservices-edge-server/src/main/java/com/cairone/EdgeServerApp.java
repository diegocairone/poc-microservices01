package com.cairone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class EdgeServerApp {

	public static void main(String[] args) {
		SpringApplication.run(EdgeServerApp.class, args);
	}

	@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("sumModule", r -> r.path("/api/sum/**")
                        .uri("lb://SUM-SERVICE"))

                .route("prodModulo", r -> r.path("/api/prod/**")
                        .uri("lb://PROD-SERVICE"))
                .build();
    }
}
