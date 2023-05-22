package com.dis.readit.dtos.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddressInputDto implements Serializable {

	@JsonProperty("userEmail")
	private String userEmail;

	@JsonProperty("strada")
	private String strada;

	@JsonProperty("numar")
	private Integer numar;

	@JsonProperty("bloc")
	private String bloc;

	@JsonProperty("scara")
	private String scara;

	@JsonProperty("numarApartament")
	private Integer numarApartament;

	@JsonProperty("idLocalitate")
	private Integer idLocalitate;

}
