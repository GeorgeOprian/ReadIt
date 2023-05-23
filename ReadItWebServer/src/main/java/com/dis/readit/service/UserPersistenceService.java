package com.dis.readit.service;

import com.dis.readit.dtos.users.UserCreateDto;
import com.dis.readit.dtos.users.UserDto;

public interface UserPersistenceService {
	UserCreateDto addUser(UserCreateDto userDto);

	UserDto getUserDetails(String email);
}
