package com.dis.readit.service.impl;

import com.dis.readit.dtos.SubscriptionDto;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.exception.ExpiredSubscription;
import com.dis.readit.model.subscription.Subscription;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.repository.SubscriptionRepository;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
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

		DataBaseUser user =  getUserFromDb(dto.getUserEmail());

		Subscription bo = new Subscription();
		bo.setStartDate(dto.getStartDate());
		bo.setEndDate(dto.getEndDate());
		bo.setUser(user);

		Subscription savedSubscription = subscriptionRepository.save(bo);

		return createSubscriptionDto(user, savedSubscription);
	}

	private DataBaseUser getUserFromDb(String email) {
		Optional<DataBaseUser> userOptional = userRepository.findUserByEmail(email);

		userOptional.orElseThrow(() -> new EntityNotFound("User with email " + email + " was not found"));
		return userOptional.get ();
	}

	private static SubscriptionDto createSubscriptionDto(DataBaseUser user, Subscription savedSubscription) {
		SubscriptionDto out = new SubscriptionDto();

		out.setSubscriptionId(savedSubscription.getSubscriptionId());
		out.setStartDate(savedSubscription.getStartDate());
		out.setEndDate(savedSubscription.getEndDate());
		out.setUserEmail(user.getEmail());
		return out;
	}

	@Override
	public SubscriptionDto checkAvailability(String email) {

		DataBaseUser user = getUserFromDb(email);

		LocalDate today = LocalDate.now();

		List<Subscription> validSubscriptions = user.getSubscriptions().stream()
				.filter(subscription -> today.isBefore(subscription.getEndDate()))
				.sorted(Comparator.comparing(Subscription::getEndDate))
				.toList();

		if (validSubscriptions.isEmpty()) {
			throw new ExpiredSubscription("User " + user.getUserName() + " does not have valid subscription");
		}

		return createSubscriptionDto(user, validSubscriptions.get(0));
	}
}
