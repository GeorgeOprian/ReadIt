package com.dis.readit.controller;

import com.dis.readit.dtos.SubscriptionDto;
import com.dis.readit.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionsController {

	@Autowired
	private SubscriptionService service;

	@PostMapping("/add")
	public ResponseEntity<SubscriptionDto> createSubscription(@RequestBody SubscriptionDto dto) {
		return ResponseEntity.ok(service.createSubscription(dto));
	}

	@GetMapping("/checkavailability")
	public ResponseEntity<SubscriptionDto> getAvailability(@RequestParam(name = "email") String email) {
		return ResponseEntity.ok(service.checkAvailability(email));
	}

}
