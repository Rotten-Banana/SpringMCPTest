package com.suriyaprakhash.mcp_server;

import com.suriyaprakhash.mcp_server.products.ProductService;
import com.suriyaprakhash.mcp_server.weather.WeatherService;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class McpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpServerApplication.class, args);
	}


//	@Bean
//	public ToolCallbackProvider weatherTools(ProductService productService) {
//		return MethodToolCallbackProvider.builder().toolObjects(productService).build();
//	}
//
	@Bean
	public List<ToolCallback> suriyaTools(ProductService productService) {
		return List.of(ToolCallbacks.from(productService));
	}

//	public record TextInput(String input) {
//	}
//
//	@Bean
//	public ToolCallback toUpperCase() {
//		return FunctionToolCallback.builder("toUpperCase", (TextInput input) -> input.input().toUpperCase())
//				.inputType(TextInput.class)
//				.description("Put the text to upper case")
//				.build();
//	}
}
