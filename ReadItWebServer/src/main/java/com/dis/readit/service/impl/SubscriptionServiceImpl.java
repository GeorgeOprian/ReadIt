package com.dis.readit.service.impl;

import com.dis.readit.dtos.SubscriptionDto;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.model.subscription.Subscription;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.repository.SubscriptionRepository;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private final UserRepository userRepository;

	private final SubscriptionRepository subscriptionRepository;

	public SubscriptionServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
		this.userRepository = userRepository;
		this.subscriptionRepository = subscriptionRepository;
	}

	@Override
	public SubscriptionDto createSubscription(SubscriptionDto dto) {

		Optional<DataBaseUser> userOptional = userRepository.findUserByEmail(dto.getUserEmail());

		userOptional.orElseThrow(() -> new EntityNotFound("User with email " + dto.getUserEmail() + " was not found"));

		DataBaseUser user = userOptional.get();

		Subscription bo = new Subscription();
		bo.setStartDate(dto.getStartDate());
		bo.setEndDate(dto.getEndDate());
		bo.setUser(user);

		Subscription savedSubscription = subscriptionRepository.save(bo);

		return createSubscriptionDto(user, savedSubscription);
	}

	private static SubscriptionDto createSubscriptionDto(DataBaseUser user, Subscription savedSubscription) {
		SubscriptionDto out = new SubscriptionDto();

		out.setSubscriptionId(savedSubscription.getSubscriptionId());
		out.setStartDate(savedSubscription.getStartDate());
		out.setEndDate(savedSubscription.getEndDate());
		out.setUserEmail(user.getEmail());
		return out;
	}

}
