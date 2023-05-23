package com.dis.readit.service.impl;

import com.dis.readit.dtos.users.UserCreateDto;
import com.dis.readit.dtos.users.UserDto;
import com.dis.readit.mapper.UserMapper;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.rabbitmq.RabbitMQMessageProducer;
import com.dis.readit.rabbitmq.requests.UserRequest;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.UserLoaderService;
import com.dis.readit.service.UserPersistenceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class UserPersistenceServiceImpl implements UserPersistenceService {

	private final UserRepository repository;
	private final UserMapper mapper;

	private final RabbitMQMessageProducer messageProducer;

	private final ObjectMapper objectMapper;

	private final UserLoaderService userLoaderService;

	public UserPersistenceServiceImpl(UserRepository repository, UserMapper mapper, RabbitMQMessageProducer messageProducer, ObjectMapper objectMapper, UserLoaderService userLoaderService) {
		this.repository = repository;
		this.mapper = mapper;
		this.messageProducer = messageProducer;
		this.objectMapper = objectMapper;
		this.userLoaderService = userLoaderService;
	}

	@Override
	public UserCreateDto addUser(UserCreateDto userDto) {

		Optional<DataBaseUser> foundUser = repository.findUserByEmail(userDto.getEmail());

		if(foundUser.isPresent()) {
			return mapper.mapToUserCreateDto(foundUser.get());
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

		return mapper.mapToUserCreateDto(savedDataBaseUser);
	}

	@Override
	public UserDto getUserDetails(String email) {
		DataBaseUser user = userLoaderService.getUserByEmail(email);
		return userLoaderService.mapUserToDto(user);
	}
}
