package com.monka.inventorydetails.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monka.inventorydetails.model.Inventory;

import reactor.core.publisher.Mono;

@Service
public class InventoryDetailOrchestrationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryDetailOrchestrationService.class);
	
	private SampleService service;
	
	@Autowired
	public InventoryDetailOrchestrationService(SampleService service) {
		this.service = service;
	}
	
	public Mono<Inventory> getInventoryDetails(String inventoryId) {
		Mono<String> descOne = service.getDescription(inventoryId);
		Mono<String> descTwo = service.getDescription(inventoryId);
		LOGGER.info("Inside InventoryDetailOrchestrationService - before mono zip");
		Mono<Inventory> monoInv = Mono.zip(descOne, descTwo).map(tuple ->{
			Inventory order = new Inventory();
			order.setInventoryId(inventoryId);
			order.setInventoryDescription(tuple.getT1() + tuple.getT2());
			order.setInventoryDate(new Date().toString());
			return order;
		});
		LOGGER.info("Inside InventoryDetailOrchestrationService - after mono zip");
		
		return monoInv;
	}

}
