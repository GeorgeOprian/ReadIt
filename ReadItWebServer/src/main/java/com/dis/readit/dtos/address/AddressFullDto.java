package com.dis.readit.dtos.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressFullDto {
	@JsonProperty("adresa")
	private AddressDto address;

	@JsonProperty("localitate")
	private LocalitateDto localitate;

	@JsonProperty("judet")
	private JudetDto judet;

}
