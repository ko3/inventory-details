package com.monka.inventorydetails.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.monka.inventorydetails.model.Inventory;

@RestController
public class InventoryController {
	
private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);
	
	@GetMapping("/inventory")
	public ResponseEntity<List<Inventory>> getOrders(){
		List<Inventory> inventory = new ArrayList<>();
		LOGGER.info("Inside /orders; returning list of orders");
		return new ResponseEntity<List<Inventory>>(inventory, HttpStatus.OK);
	}
	
	@GetMapping("/inventory/{inventoryId}")
	public ResponseEntity<Inventory> getOrder(@PathVariable String inventoryId){
		Inventory order = new Inventory();
		order.setInventoryId(inventoryId);
		order.setInventoryDescription("Sample order");
		order.setInventoryDate(new Date().toString());
		LOGGER.info("Inside /orders/{orderId}; returning the order");
		return new ResponseEntity<Inventory>(order, HttpStatus.OK);
	}

}
