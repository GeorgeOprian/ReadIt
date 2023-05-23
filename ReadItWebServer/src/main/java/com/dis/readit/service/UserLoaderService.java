package com.dis.readit.service;

import com.dis.readit.dtos.users.UserDto;
import com.dis.readit.model.user.DataBaseUser;

public interface UserLoaderService {

	DataBaseUser getUserByEmail(String email);

	UserDto mapUserToDto(DataBaseUser user);
}
