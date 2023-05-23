package com.dis.readit.dtos.users;

import com.dis.readit.dtos.address.AddressFullDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserDto extends UserCreateDto {

	@JsonProperty("userId")
	private Integer userId;

	@JsonProperty("address")
	private AddressFullDto addressDto;

}
