package com.dis.readit.mapper;

import com.dis.readit.dtos.input.users.UserCreateDto;
import com.dis.readit.dtos.output.user.UserDto;
import com.dis.readit.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto mapToDto(User user);

	User mapToModel(UserCreateDto user);
}
