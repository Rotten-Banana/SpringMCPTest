package com.suriyaprakhash.mcp_server;

import com.suriyaprakhash.mcp_server.products.ProductService;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class McpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpServerApplication.class, args);
	}

	@Bean
	public List<ToolCallback> suriyaTools(ProductService productService) {
		return List.of(ToolCallbacks.from(productService));
	}

}
