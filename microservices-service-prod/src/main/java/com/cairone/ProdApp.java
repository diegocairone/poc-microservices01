package com.cairone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@EnableEurekaClient
@SpringBootApplication
public class ProdApp {

	public static void main(String[] args) {
		SpringApplication.run(ProdApp.class, args);
	}

	@GetMapping("/api/prod")
	public ResponseEntity<Response> productory(long a, long b) {
	    Long result = 0L;
	    for (int i = 0; i < b; i++) {
	        Response sum = restTemplate.getForObject(
	                "lb://SUM-SERVICE/api/sum?a="+result+"&b="+a, Response.class);
	        result = sum.getResult();
	    }
	    log.info("Productory of {} * {} = {}", a, b, result);
	    return ResponseEntity.ok(new Response(result));
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
	    
	    private long result;
	}
	
	@LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Autowired
	private RestOperations restTemplate;
}
