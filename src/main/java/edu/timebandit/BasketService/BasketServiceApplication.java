package edu.timebandit.BasketService;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BasketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasketServiceApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new io.swagger.v3.oas.models.Components())
				.info(new io.swagger.v3.oas.models.info.Info()
						.title("Basket Service API")
						.version("v1")
						.description("Basket Service API for TimeBandit"));
	}

}
