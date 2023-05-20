package com.dis.readit.service;

import com.dis.readit.dtos.SubscriptionDto;

public interface SubscriptionService {
	SubscriptionDto createSubscription(SubscriptionDto dto);

	SubscriptionDto checkAvailability(String email);
}
