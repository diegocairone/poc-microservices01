package com.cairone;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
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
                /*
                .route("openapi", r -> r
                        .path("/v3/api-docs/prod/**")
                        .filters(f -> f.rewritePath(
                                "/v3/api-docs/prod/prod", 
                                "/prod/v3/api-docs/**"))
                        .uri("http://localhost:7000")
                )*/
                .build();
    }
	

    //@Autowired
    RouteDefinitionLocator locator;

    //@Bean
    public List<GroupedOpenApi> apis() {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")).forEach(routeDefinition -> {
            String name = routeDefinition.getId().replaceAll("-service", "");
            groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build());
        });
        return groups;
    }
}
