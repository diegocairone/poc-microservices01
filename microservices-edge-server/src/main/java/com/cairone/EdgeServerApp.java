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
    
    // To add Swagger see: 
    // https://piotrminkowski.com/2020/02/20/microservices-api-documentation-with-springdoc-openapi/
    // and
    // https://stackoverflow.com/questions/66953605/spring-cloud-gateway-and-springdoc-openapi-integration

	public static void main(String[] args) {
		SpringApplication.run(EdgeServerApp.class, args);
	}

	@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("sum-service", r -> r
                        .path("/sum/**")
                        .filters(f -> f.rewritePath("/sum(?<segment>/?.*)", "$\\{segment}"))
                        .uri("lb://SUM-SERVICE")
                )
                .route("prod-service", r -> r
                        .path("/prod/**")
                        .filters(f -> f.rewritePath("/prod(?<segment>/?.*)", "$\\{segment}"))
                        .uri("lb://PROD-SERVICE")
                )
                .build();
    }
	
}
