package com.suriyaprakhash.inventory_mgnt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

	@Bean
	public OpenAPI refrigeratorInventoryOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Refrigerator Inventory Management API")
						.description("API for managing refrigerator inventory products")
						.version("v0.0.1")
						.contact(new Contact()
								.name("Suriya Prakhash")
								.url("https://github.com/suriyaprakhash/refrigerator-inventory-mgnt"))
						.license(new License()
								.name("MIT License")
								.url("https://opensource.org/licenses/MIT")));
	}
}
