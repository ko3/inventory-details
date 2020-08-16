package com.monka.inventorydetails.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import reactor.core.publisher.Mono;

@Service
public class SampleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleService.class);
	
	private WebClient webClient;
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	public SampleService(WebClient webClient, WebClient.Builder webClientBuilder) {
		this.webClient = webClient;
		this.webClientBuilder = webClientBuilder;
	}
	
	public Mono<String> getDescription(String id) {
		LOGGER.info("Inside SampleService: {}", id);
	 
	    return webClient.get()
	        .uri("/get")
	        .retrieve()
	        .bodyToMono(String.class);
	}

}
