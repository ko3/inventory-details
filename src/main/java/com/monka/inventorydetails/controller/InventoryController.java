package com.monka.inventorydetails.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.monka.inventorydetails.model.Inventory;
import com.monka.inventorydetails.service.InventoryDetailOrchestrationService;

import reactor.core.publisher.Mono;

@RestController
public class InventoryController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);
	
	private InventoryDetailOrchestrationService orchestrationService;
	
	@Autowired
	public InventoryController(InventoryDetailOrchestrationService orchestrationService) {
		this.orchestrationService = orchestrationService;
	}
	
	@GetMapping("/inventory")
	public Mono<List<Inventory>> getOrders(){
		List<Inventory> inventory = new ArrayList<>();
		LOGGER.info("Inside /orders; returning list of orders");
		return Mono.just(inventory);
	}
	
	@GetMapping("/inventory/{inventoryId}")
	public Mono<Inventory> getOrder(@PathVariable String inventoryId){
		LOGGER.info("Inside /orders/{orderId}; returning the order");
		return orchestrationService.getInventoryDetails(inventoryId);
	}

}
