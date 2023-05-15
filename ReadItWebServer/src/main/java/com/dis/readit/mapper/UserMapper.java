package com.dis.readit.mapper;

import com.dis.readit.dtos.input.users.UserCreateDto;
import com.dis.readit.dtos.output.user.UserDto;
import com.dis.readit.model.user.DataBaseUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto mapToDto(DataBaseUser dataBaseUser);

	DataBaseUser mapToModel(UserCreateDto user);
}
