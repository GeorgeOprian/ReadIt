package com.dis.readit.service.impl;

import com.dis.readit.dtos.input.users.UserCreateDto;
import com.dis.readit.mapper.UserMapper;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository repository;
	private final UserMapper mapper;

	public UserServiceImpl(UserRepository repository, UserMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public UserCreateDto addUser(UserCreateDto userDto) {

		Optional<DataBaseUser> foundUser = repository.findUserByEmail(userDto.getEmail());

		if(foundUser.isPresent()) {
			return mapper.mapToDto(foundUser.get());
		}

		DataBaseUser savedDataBaseUser = repository.save(mapper.mapToModel(userDto));

		return mapper.mapToDto(savedDataBaseUser);
	}
}
