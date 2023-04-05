package com.dis.readit.dtos.output.user;

import com.dis.readit.dtos.input.users.UserCreateDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserDto extends UserCreateDto {

	@JsonProperty("userId")
	private Integer userId;

}
