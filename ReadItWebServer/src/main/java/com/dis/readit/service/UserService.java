package com.dis.readit.service;

import com.dis.readit.dtos.users.UserCreateDto;

public interface UserService {
	UserCreateDto addUser(UserCreateDto userDto);

}
