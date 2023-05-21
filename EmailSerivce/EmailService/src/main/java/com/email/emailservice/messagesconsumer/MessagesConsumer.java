package com.email.emailservice.messagesconsumer;

import com.email.emailservice.Dtos.EmailRequest;
import com.email.emailservice.Dtos.UserRequest;
import com.email.emailservice.service.EmailService;
import com.email.emailservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Slf4j
@Component
public class MessagesConsumer {

	private final EmailService emailService;
	private final UserService userService;
	private final ObjectMapper mapper;
	private final ThreadPoolTaskExecutor taskExecutor;

	public MessagesConsumer(EmailService emailService, UserService userService, ObjectMapper mapper, ThreadPoolTaskExecutor taskExecutor) {
		this.emailService = emailService;
		this.userService = userService;
		this.mapper = mapper;
		this.taskExecutor = taskExecutor;
	}

	@RabbitListener(queues = { "${queue.email}" })
	public void receiveEmails(@Payload String fileBody) throws JsonProcessingException {
		if (!fileBody.isEmpty()) {
			EmailRequest request = mapper.readValue(fileBody, EmailRequest.class);
			CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> emailService.sendEmail(request), taskExecutor);
			try {
				completableFuture.join();
			} catch (CompletionException e) {
				log.info(e.getMessage(), e);
			}
		}
		System.out.println(fileBody);
	}

	@RabbitListener(queues = { "${queue.userChanges}" })
	public void receiveUserChanges(@Payload String fileBody) throws JsonProcessingException {
		if (!fileBody.isEmpty()) {
			UserRequest request = mapper.readValue(fileBody, UserRequest.class);
			CompletableFuture.runAsync(() -> userService.handleUserOperation(request), taskExecutor);
		}
		System.out.println(fileBody);
	}
}


