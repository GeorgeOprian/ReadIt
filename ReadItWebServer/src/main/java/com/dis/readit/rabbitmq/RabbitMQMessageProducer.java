package com.dis.readit.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessageProducer {

	public static final String USER_CHANGES_ROUTING_KEY = "UserChangesQueue";
	public static final String EMAIL_ROUTING_KEY = "EmailQueue";
	private final RabbitTemplate rabbitTemplate;

	public RabbitMQMessageProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessage(String routingKey, String message) {
		rabbitTemplate.convertAndSend(routingKey, message);
	}
}
