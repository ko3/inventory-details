package com.monka.inventorydetails.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.monka.inventorydetails.config.context.RequestAccessConfigurer;

@Configuration
@Import({RequestAccessConfigurer.class})
public class CoreConfig {
	
	@Bean
	public CustomWebFilter getCustomWebFilter() {
		return new CustomWebFilter();
	}

}
