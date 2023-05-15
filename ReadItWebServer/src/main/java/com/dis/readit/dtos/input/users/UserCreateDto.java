package com.dis.readit.dtos.input.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserCreateDto {

	@NotNull
	@JsonProperty("email")
	private String email;

	@NotNull
	@JsonProperty("userName")
	private String userName;

}
