package com.dis.readit.dtos.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressFullDto extends AddressDto {

	@JsonProperty("localitate")
	private String localitate;

	@JsonProperty("judet")
	private String judet;

}
