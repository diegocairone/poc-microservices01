package com.cairone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
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
	    Long result = a * b;
	    log.info("Productory of {} * {} = {}", a, b, result);
	    return ResponseEntity.ok(new Response(result));
	}
	
	@Data
	@AllArgsConstructor
	public static class Response {
	    
	    private long result;
	}
}
