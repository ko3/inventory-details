package com.monka.inventorydetails.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

import com.monka.inventorydetails.config.context.RequestAccessConfigurer;

@Configuration
@Import({RequestAccessConfigurer.class})
@EnableWebFlux
public class CoreConfig {
	
	@Bean
	public CustomWebFilter getCustomWebFilter() {
		return new CustomWebFilter();
	}
	
	@Bean
	public WebClient getWebClient(WebClient.Builder webClientBuilder) {
		return webClientBuilder.baseUrl("http://httpbin.org").build();
	}

}
