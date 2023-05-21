package com.dis.readit.service.impl;

import com.dis.readit.dtos.users.UserCreateDto;
import com.dis.readit.mapper.UserMapper;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.rabbitmq.RabbitMQMessageProducer;
import com.dis.readit.rabbitmq.requests.UserRequest;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository repository;
	private final UserMapper mapper;

	private final RabbitMQMessageProducer messageProducer;

	private final ObjectMapper objectMapper;

	public UserServiceImpl(UserRepository repository, UserMapper mapper, RabbitMQMessageProducer messageProducer, ObjectMapper objectMapper) {
		this.repository = repository;
		this.mapper = mapper;
		this.messageProducer = messageProducer;
		this.objectMapper = objectMapper;
	}

	@Override
	public UserCreateDto addUser(UserCreateDto userDto) {

		Optional<DataBaseUser> foundUser = repository.findUserByEmail(userDto.getEmail());

		if(foundUser.isPresent()) {
			return mapper.mapToDto(foundUser.get());
		}

		DataBaseUser userToSave = mapper.mapToModel(userDto);

		if (userToSave.getEmail().equals(DataBaseUser.ADMIN_USER_EMAIL)) {
			userToSave.setAdmin(true);
		}

		DataBaseUser savedDataBaseUser = repository.save(userToSave);

		UserRequest userRequest = UserRequest.createForOperation(savedDataBaseUser, UserRequest.USER_OPERATION_CREATE);

		try {
			messageProducer.sendMessage(RabbitMQMessageProducer.USER_CHANGES_ROUTING_KEY, objectMapper.writeValueAsString(userRequest));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}

		return mapper.mapToDto(savedDataBaseUser);
	}
}
