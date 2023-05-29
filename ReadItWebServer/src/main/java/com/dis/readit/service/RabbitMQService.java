package com.dis.readit.service;

public interface RabbitMQService {

	void sendMessageToEmailService(String routingKey, Object message);
}
