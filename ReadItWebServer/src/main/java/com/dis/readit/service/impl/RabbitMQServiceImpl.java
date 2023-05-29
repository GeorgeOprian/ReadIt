package com.dis.readit.service.impl;

import com.dis.readit.rabbitmq.RabbitMQMessageProducer;
import com.dis.readit.service.RabbitMQService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQServiceImpl implements RabbitMQService {

	private final RabbitMQMessageProducer messageProducer;

	private final ObjectMapper objectMapper;

	public RabbitMQServiceImpl(RabbitMQMessageProducer messageProducer, ObjectMapper objectMapper) {
		this.messageProducer = messageProducer;
		this.objectMapper = objectMapper;
	}

	@Override
	public void sendMessageToEmailService(String routingKey, Object message) {
		try {
			messageProducer.sendMessage(routingKey, objectMapper.writeValueAsString(message));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
	}
}
