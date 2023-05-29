package com.dis.readit.service.impl;

import com.dis.readit.dtos.SubscriptionDto;
import com.dis.readit.exception.ExpiredSubscription;
import com.dis.readit.model.subscription.Subscription;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.rabbitmq.RabbitMQMessageProducer;
import com.dis.readit.rabbitmq.requests.EmailRequest;
import com.dis.readit.repository.SubscriptionRepository;
import com.dis.readit.service.RabbitMQService;
import com.dis.readit.service.SubscriptionService;
import com.dis.readit.service.UserLoaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private final SubscriptionRepository subscriptionRepository;

	private final RabbitMQService rabbitMQService;

	private final UserLoaderService userLoaderService;

	public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, RabbitMQService rabbitMQService, UserLoaderService userLoaderService) {
		this.subscriptionRepository = subscriptionRepository;
		this.rabbitMQService = rabbitMQService;
		this.userLoaderService = userLoaderService;
	}

	@Override
	public SubscriptionDto createSubscription(SubscriptionDto dto) {

		DataBaseUser user =  userLoaderService.getUserByEmail(dto.getUserEmail());

		Subscription bo = new Subscription();
		bo.setStartDate(dto.getStartDate());
		bo.setEndDate(dto.getEndDate());
		bo.setUser(user);

		Subscription savedSubscription = subscriptionRepository.save(bo);

		DataBaseUser adminUser =  userLoaderService.getUserByEmail(DataBaseUser.ADMIN_USER_EMAIL);

		String subject = "New ReadIt Subscription";
		String emailBody = "Hi " + user.getUserName() + ",\n\nA new Subscription was added for you, check out the app to see more details.";

		EmailRequest emailRequest = EmailRequest.createEmailForUser(adminUser.getUserId(), Arrays.asList(user.getUserId()), subject, emailBody);

		rabbitMQService.sendMessageToEmailService(RabbitMQMessageProducer.EMAIL_ROUTING_KEY, emailRequest);

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

	@Override
	public SubscriptionDto checkAvailability(String email) {
		DataBaseUser user =  userLoaderService.getUserByEmail(email);

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
