package com.dis.readit.service.impl;

import com.dis.readit.dtos.users.UserCreateDto;
import com.dis.readit.dtos.users.UserDto;
import com.dis.readit.mapper.UserMapper;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.rabbitmq.RabbitMQMessageProducer;
import com.dis.readit.rabbitmq.requests.EmailRequest;
import com.dis.readit.rabbitmq.requests.UserRequest;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.RabbitMQService;
import com.dis.readit.service.UserLoaderService;
import com.dis.readit.service.UserPersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserPersistenceServiceImpl implements UserPersistenceService {

	private final UserRepository repository;
	private final UserMapper mapper;

	private final RabbitMQService rabbitMQService;

	private final UserLoaderService userLoaderService;

	public UserPersistenceServiceImpl(UserRepository repository, UserMapper mapper, RabbitMQService rabbitMQService,
			UserLoaderService userLoaderService) {
		this.repository = repository;
		this.mapper = mapper;
		this.rabbitMQService = rabbitMQService;
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

		rabbitMQService.sendMessageToEmailService(RabbitMQMessageProducer.USER_CHANGES_ROUTING_KEY, userRequest);

		return mapper.mapToUserCreateDto(savedDataBaseUser);
	}



	@Override
	public UserDto getUserDetails(String email) {
		DataBaseUser user = userLoaderService.getUserByEmail(email);
		return userLoaderService.mapUserToDto(user);
	}

	@Override
	public Void sendEmailToAllUsers(String adminEmail) {
		DataBaseUser user = userLoaderService.getUserByEmail(adminEmail);

		List<Integer> allUsersIds = repository.findAll().stream().map(DataBaseUser::getUserId).collect(Collectors.toList());

		String emailSubject = "New Books Added";

		String emailBody = "Hi, " + user.getUserName() + ",\n\nNew books Were added in ReadIt App.\nHurry up to rent one of them.";

		EmailRequest emailRequest = EmailRequest.createEmailForUser(user.getUserId(), allUsersIds, emailSubject, emailBody);

		rabbitMQService.sendMessageToEmailService(RabbitMQMessageProducer.EMAIL_ROUTING_KEY, emailRequest);

		return null;
	}
}
